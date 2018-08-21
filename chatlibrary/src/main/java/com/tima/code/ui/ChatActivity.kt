package com.tima.code.ui

import android.os.Bundle
import android.view.View
import com.tima.code.R
import com.tima.code.bean.MsgInfo
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.chat_activity_chat_layout.*
import kotlinx.android.synthetic.main.chat_send_msg_buttom_layout.*

/**
 * Created by Administrator on 2018/8/20/020.
 */
class ChatActivity : BaseActivity() , View.OnClickListener{

    var msgInfos = ArrayList<MsgInfo>()                                                             //

    override fun inits(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        tv_age.text="23"

        iv_send.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return R.layout.chat_activity_chat_layout
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_send -> {

            }
        }
    }


}