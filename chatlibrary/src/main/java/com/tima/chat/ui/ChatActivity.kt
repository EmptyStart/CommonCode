package com.tima.chat.ui

import android.os.Bundle
import android.view.View
import com.tima.chat.R
import com.tima.chat.apdater.ChatAdapter
import com.tima.chat.bean.MsgInfo
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.chat_activity_chat_layout.*
import kotlinx.android.synthetic.main.chat_send_msg_buttom_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Administrator on 2018/8/20/020.
 */
class ChatActivity : BaseActivity() , View.OnClickListener{

    var msgInfos = ArrayList<MsgInfo>()                                                             //聊天信息
    var chapAdapter : ChatAdapter = null!!

    override fun inits(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        tv_age.text="23"
        iv_send.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.chat_activity_chat_layout
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_send -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}