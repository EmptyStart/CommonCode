package com.tima.chat.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.GridView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.chat.R
import com.tima.chat.constracts.IChatView
import com.tima.chat.helper.ChatUtils
import com.tima.chat.helper.ChatUtils.REFRESH_CHAT
import com.tima.chat.helper.ChatUtils.TAG
import com.tima.chat.ui.presenter.ChatPersenterImpl
import com.tima.chat.weight.RecordVoiceBtnController
import com.tima.common.base.BaseActivity
import com.tima.common.base.MsgEventData
import com.tima.common.base.RoutePaths
import com.tima.common.utils.LogUtils
import kotlinx.android.synthetic.main.chat_activity_chat_layout.*
import kotlinx.android.synthetic.main.chat_send_msg_buttom_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Administrator on 2018/8/20/020.
 */
@Route(path = RoutePaths.chatActivity)
class ChatActivity : BaseActivity() , View.OnClickListener,IChatView ,SwipeRefreshLayout.OnRefreshListener{
    var ichatPresent : ChatPersenterImpl? = null
    var friendId : String = ""                                                                      //环信对方账号

    override fun inits(savedInstanceState: Bundle?) {
        friendId = intent.getStringExtra("friendId")
        ichatPresent = ChatPersenterImpl(this)
        lifecycle.addObserver(ichatPresent!!)

        iv_add.setOnClickListener(this)
        iv_send.setOnClickListener(this)
        iv_voice.setOnClickListener(this)
        reycler_chat.setOnClickListener(this)
        swipe_refresh.setOnRefreshListener(this)
        abv_type2.setOnRightImageListener(this)
        abv_type2.setTitle(friendId)

        ichatPresent!!.init()
    }

    override fun useEventBus(): Boolean =true
    override fun onRefresh() {
        if (ichatPresent != null)
            ichatPresent!!.refreshChatAdapter()
        swipe_refresh.isRefreshing = false
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun reciviceBus(event: MsgEventData) {
        LogUtils.i(TAG,"刷新接收的内容 event=="+event)
        when(event.functionName){
            REFRESH_CHAT->{
                LogUtils.i(TAG,"刷新接收的内容")
                if (ichatPresent != null)
                    ichatPresent?.refreshChatAdapter()
            }
        }
    }

    override fun getFRIENDID(): String {
        return friendId
    }
}