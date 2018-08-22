package com.tima.chat.Listener

import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage
import com.tima.chat.bean.MsgInfo
import com.tima.common.utils.DateUtils
import com.tima.common.utils.LogUtils

/**
 *  消息监听
 * Created by Administrator on 2018/8/21/021.
 */
class NyEMMessageListener{/* : EMMessageListener{

    override fun onMessageRecalled(p0: MutableList<EMMessage>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMessageRead(p0: MutableList<EMMessage>?) {
    }


    override fun onMessageChanged(p0: EMMessage?, p1: Any?) {
        //消息状态变动
    }

    override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {
        //收到透传消息
    }

    override fun onMessageReceived(messages : MutableList<EMMessage>?) {
        //收到消息
        if (messages != null && messages.size > 0){
            for (i in 0..messages.size - 1){
                var message = messages[i]
                var msg = MsgInfo()
                msg.content = message.body.toString()
                msg.msgType =  message.type
                msg.acceptTime = DateUtils.getDateYMDHMS(System.currentTimeMillis())
                msg.from = message.from

                LogUtils.i("NyEMMessageListener","接收到消息=="+message.toString())
            }
        }

    }

    override fun onMessageDelivered(p0: MutableList<EMMessage>?) {
    }*/

}