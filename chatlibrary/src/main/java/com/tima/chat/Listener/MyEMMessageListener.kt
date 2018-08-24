package com.tima.chat.Listener

import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage
import com.tima.chat.bean.MsgInfo
import com.tima.chat.helper.ChatUtils
import com.tima.common.base.MsgEventData
import com.tima.common.utils.DateUtils
import com.tima.common.utils.LogUtils
import org.greenrobot.eventbus.EventBus

/**
 *  消息监听
 * Created by Administrator on 2018/8/21/021.
 */
class MyEMMessageListener: EMMessageListener{
    var TAG = "MyEMMessageListener"

    /*override fun onMessageRecalled(p0: MutableList<EMMessage>?) {
    }*/

    override fun onMessageRead(p0: MutableList<EMMessage>?) {
        LogUtils.i(TAG,"onMessageRead=="+ p0!!.toString())
    }


    override fun onMessageChanged(p0: EMMessage?, p1: Any?) {
        //消息状态变动
        LogUtils.i(TAG,"消息状态变动=="+ p0!!.toString())
    }

    override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {
        //收到透传消息
        LogUtils.i(TAG,"收到透传消息=="+ p0!!.size)
    }

    override fun onMessageReceived(messages : MutableList<EMMessage>?) {
        LogUtils.i(TAG,"收到消息=="+ messages!!.size)
        //收到消息
        if (messages != null && messages.size > 0){
            ChatUtils.msgInfos.clear()
            for (i in 0..messages.size - 1){
                var message = messages[i]
                var msg = MsgInfo()
                msg.content = message.body.toString()
                msg.msgType =  message.type
                msg.acceptTime = DateUtils.getDateYMDHMS(System.currentTimeMillis())
                msg.from = message.from

                ChatUtils.msgInfos.add(msg)
                LogUtils.i(TAG,"接收到消息=="+msg.toString())
            }

            if (ChatUtils.msgInfos.size > 0){
                EventBus.getDefault().post(MsgEventData(ChatUtils.REFRESH_CHAT,null))
            }
        }
    }

    override fun onMessageDelivered(p0: MutableList<EMMessage>?) {
        LogUtils.i(TAG,"onMessageDelivered=="+ p0!!.toString())
    }

}