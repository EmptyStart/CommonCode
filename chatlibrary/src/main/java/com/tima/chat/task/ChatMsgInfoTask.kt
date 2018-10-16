package com.tima.chat.task

import android.text.TextUtils
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.chat.EMVoiceMessageBody
import com.tima.chat.helper.ChatUtils
import com.tima.common.base.MsgEventData
import com.tima.common.base.MsgInfo
import com.tima.common.base.PresonInfo
import com.tima.common.thread.TaskInterface
import com.tima.common.utils.DateUtils
import com.tima.common.utils.LogUtils
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal

/**
 * Created by Administrator on 2018/9/18.
 */
class ChatMsgInfoTask(var messages : MutableList<EMMessage>): TaskInterface{
    var TAG = "ChatMsgInfoTask"
    override fun run() {
        super.run()
        ChatUtils.msgInfos.clear()
        saveMsgInfo(messages)
        savePresonInfo(messages)
        if (ChatUtils.msgInfos.size > 0){
            EventBus.getDefault().post(MsgEventData(ChatUtils.REFRESH_CHAT,null))
        }
    }

    /**
     * 保存消息
     */
    fun saveMsgInfo(messages : MutableList<EMMessage>){
        for (i in 0..messages.size - 1){
            var message = messages[i]
            var msg = MsgInfo()
            if (message.type == EMMessage.Type.TXT){
                var txtBody : EMTextMessageBody = message.body as EMTextMessageBody
                msg.content = txtBody.message
                LogUtils.i(TAG,"接收到消息  文本内容 =="+txtBody.message)
            }else if (message.type == EMMessage.Type.VOICE){
                var voiceBody : EMVoiceMessageBody = message.body as EMVoiceMessageBody
                msg.content = voiceBody.localUrl
                msg.voiceLeght = voiceBody.length
                LogUtils.i(TAG,"接收到消息  语音 voiceBody =="+voiceBody+"    "+voiceBody.length)
                LogUtils.i(TAG,"接收到消息  语音 localUrl =="+voiceBody.localUrl +"   downloadStatus=="+voiceBody.downloadStatus())
            }
            msg.msgType =  message.type
            msg.type = ChatUtils.TYPE_RECEIVED
            msg.acceptTime = DateUtils.getDateYMDHMS(System.currentTimeMillis())
            msg.from = message.from
            msg.msgId = message.msgId
            msg.saveOrUpdate("msgId=?",message.msgId)

            ChatUtils.msgInfos.add(msg)
            LogUtils.i(TAG,"接收到消息=="+msg.toString())
        }
    }

    /**
     * 聊天对象信息
     */
    fun savePresonInfo(messages : MutableList<EMMessage>){
        for (i in 0..messages.size - 1){
            var message = messages[i]
            if (!TextUtils.isEmpty(message.from) && message.from.length > 0){
                var presonInfo = LitePal.where("userName=?",message.from).findFirst(PresonInfo::class.java)
                if (presonInfo == null){
                    presonInfo = PresonInfo(message.from,message.userName,1,"")
                    presonInfo.saveOrUpdate("userName=?",message.userName)
                }
            }else{
                var presonInfo = LitePal.where("userName=?",message.from).findFirst(MsgInfo::class.java)
            }

        }
    }
}