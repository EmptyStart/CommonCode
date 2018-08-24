package com.tima.chat.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.GridView
import com.tima.chat.R
import com.tima.chat.constracts.IChatPresent
import com.tima.chat.constracts.IChatView
import com.tima.chat.helper.ChatUtils
import com.tima.chat.ui.presenter.ChatPersenterImpl
import com.tima.chat.weight.RecordVoiceBtnController
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.chat_activity_chat_layout.*
import kotlinx.android.synthetic.main.chat_send_msg_buttom_layout.*

/**
 * Created by Administrator on 2018/8/20/020.
 */
class ChatActivity : BaseActivity() , View.OnClickListener,IChatView ,SwipeRefreshLayout.OnRefreshListener{

    var ichatPresent : ChatPersenterImpl? = null

    override fun inits(savedInstanceState: Bundle?) {
        ichatPresent = ChatPersenterImpl(this)
        lifecycle.addObserver(ichatPresent!!)

        iv_add.setOnClickListener(this)
        iv_send.setOnClickListener(this)
        iv_voice.setOnClickListener(this)
        reycler_chat.setOnClickListener(this)
        swipe_refresh.setOnRefreshListener(this)
        ichatPresent!!.init()
    }


    override fun onRefresh() {
        if (ichatPresent != null)
            ichatPresent!!.refreshChatAdapter()
    }

    override fun getLayoutId(): Int {
        return R.layout.chat_activity_chat_layout
    }

    override fun onClick(p0: View?) {
        ichatPresent!!.onClick(p0)
    }

    override fun getChatRecyclerView(): RecyclerView {
        return reycler_chat
    }

    override fun getGvAddChatView(): GridView {
        return gv_add_chat
    }

    override fun getEdInputSmsView(): EditText {
        return ed_input_sms
    }

    override fun getVoiceBtView(): RecordVoiceBtnController {
        return voice_btn
    }

    override fun getActivity(): AppCompatActivity {
        return this
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        ChatUtils.onDestory()
    }
}