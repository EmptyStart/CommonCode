package com.xx.chat.Listener

import com.hyphenate.chat.ChatManager
import com.hyphenate.chat.Message
import com.xx.common.utils.LogUtils

/**
 * Created by Administrator on 2018/8/27/027.
 */
class MyMessageListener : ChatManager.MessageListener {
    var TAG = "MyMessageListener"
    override fun onMessage(msgs: MutableList<Message>?) {
        LogUtils.i(TAG,"收到消息=="+ msgs!!.size)
    }

    override fun onMessageSent() {
    }

    override fun onCmdMessage(msgs: MutableList<Message>?) {
        LogUtils.i(TAG,"收到透传消息=="+ msgs!!.size)
    }

    override fun onMessageStatusUpdate() {
    }
}