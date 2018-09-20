package com.tima.chat.Listener

import android.text.TextUtils
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.chat.EMVoiceMessageBody
import com.tima.chat.helper.ChatUtils
import com.tima.chat.helper.ChatUtils.TYPE_RECEIVED
import com.tima.chat.task.ChatMsgInfoTask
import com.tima.common.base.MsgEventData
import com.tima.common.thread.ThreadPoolManager
import com.tima.common.utils.DateUtils
import com.tima.common.utils.LogUtils
import org.greenrobot.eventbus.EventBus
import org.litepal.crud.DataSupport

/**
 *  消息监听
 * Created by Administrator on 2018/8/21/021.
 */
class MyEMMessageListener: EMMessageListener{
    var TAG = "MyEMMessageListener"

    override fun onMessageRecalled(p0: MutableList<EMMessage>?) {
    }

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
        if (messages != null && messages.size > 0){
            ThreadPoolManager.getInstance().execute(ChatMsgInfoTask(messages))
        }

    }

    override fun onMessageDelivered(p0: MutableList<EMMessage>?) {
        LogUtils.i(TAG,"onMessageDelivered=="+ p0!!.toString())
    }


}