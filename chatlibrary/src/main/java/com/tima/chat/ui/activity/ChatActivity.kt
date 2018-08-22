package com.tima.chat.ui.activity

import android.os.Bundle
import android.view.View
import com.tima.chat.R
import com.tima.chat.apdater.ChatAdapter
import com.tima.chat.apdater.ChoiceFunctionAdapter
import com.tima.chat.bean.MsgInfo
import com.tima.chat.constracts.IChatPresent
import com.tima.chat.constracts.IChatView
import com.tima.chat.ui.presenter.ChatPersenterImpl
import com.tima.common.base.BaseActivity
import com.tima.common.utils.CameraUtils
import kotlinx.android.synthetic.main.chat_activity_chat_layout.*
import kotlinx.android.synthetic.main.chat_send_msg_buttom_layout.*

/**
 * Created by Administrator on 2018/8/20/020.
 */
class ChatActivity : BaseActivity() , View.OnClickListener, ChoiceFunctionAdapter.OnChoiceClickListener,IChatView {

    var ichatPresent : IChatPresent? = null

    var msgInfos = ArrayList<MsgInfo>()                                                             //聊天信息
    var chapAdapter : ChatAdapter? = null

    override fun inits(savedInstanceState: Bundle?) {
        ichatPresent = ChatPersenterImpl(this)
        lifecycle.addObserver(ichatPresent as ChatPersenterImpl)

        tv_age.text="23"
        iv_send.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.chat_activity_chat_layout
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_send -> {
                gv_add_chat.visibility = View.VISIBLE
                refreshChatAdapter()
            }
        }
    }

    var choiceAdpater : ChoiceFunctionAdapter? = null
    /**
     * 刷新拍照等适配器
     */
    private fun refreshChatAdapter() {
        if (choiceAdpater == null) {
            choiceAdpater = ChoiceFunctionAdapter(this, this)
            gv_add_chat.setAdapter(choiceAdpater)
            gv_add_chat.setNumColumns(4)
        } else {
            choiceAdpater!!.notifyDataSetChanged()
        }
    }

    override fun onClickItem(name: String) {
        if (name.equals("拍摄")) {
            var imagePath = CameraUtils.onCallCamera(this)
        } else {
            CameraUtils.onCallAlbum(this)
        }
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }
}