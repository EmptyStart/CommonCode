package com.tima.chat.helper

import android.text.TextUtils
import com.hyphenate.chat.*
import com.hyphenate.util.EMLog
import java.io.File

/**
 * 消息工具类
 * Created by Administrator on 2018/8/27/027.
 */
object MessageUtils{
    var TAG = "MessageUtils"

    /**
     * 创建语音消息
     * @param filePath 语音文件路径
     * @param timeLength 语音长度
     * @param toUserName ToUserName
     * @return 消息
     */
    fun createVoiceSendMessage(filePath: String, timeLength: Int, toUserName: String): Message? {
        if (TextUtils.isEmpty(filePath)) {
            EMLog.e(TAG, "voice filePath is null or empty")
            return null
        }
        val voiceFile = File(filePath)
        if (!voiceFile.exists()) {
            EMLog.e(TAG, "voice file does not exists")
            return null
        }
        val message = Message.createSendMessage(Message.Type.VOICE)
        val messageBody = EMVoiceMessageBody(voiceFile, timeLength)
        message.body = messageBody
        message.to = toUserName
        return message
    }

    /**
     * 发送语音
     */
     fun sendVoiceMessage(filePath: String, length: Int,friendId : String) {
        if (TextUtils.isEmpty(filePath)) {
            return
        }
        val message = EMMessage.createVoiceSendMessage(filePath, length, friendId)
        //attachMessageAttrs(message)
        EMClient.getInstance().chatManager().sendMessage(message)
        //messageList.refreshSelectLast()
    }
}