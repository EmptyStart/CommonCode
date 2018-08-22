package com.tima.chat.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMOptions
import com.tima.chat.Listener.MyEMMessageListener
import com.tima.chat.Listener.NyEMMessageListener
import com.tima.chat.ui.ChatActivity

/**
 * Created by Administrator on 2018/8/21/021.
 */
object ChatUtils{

    /**
     * 初始化聊天
     */
    fun init(context: Context) : Boolean{
        try {
            val options = EMOptions()
            options.acceptInvitationAlways = false
            //初始化
            EMClient.getInstance().init(context, options)
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(true)
            EMClient.getInstance().chatManager().addMessageListener(MyEMMessageListener())
        }catch (e : Exception){
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * 登陆环信
     */
    fun login(name : String,password : String, activity : Activity){
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            return
        }
        EMClient.getInstance().login(name, password, object : EMCallBack {
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                activity.runOnUiThread(Runnable {
                    activity.startActivity(Intent(activity, ChatActivity::class.java))
                    activity.finish()
                })
            }

            override fun onProgress(progress: Int, status: String) {
            }

            override fun onError(code: Int, message: String) {
            }
        })
    }

    /**
     *  发送消息
     */
    fun sendMsg(content : String,friendId : String) : Boolean{
        try {
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            val message = EMMessage.createTxtSendMessage(content, friendId)
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message)
        }catch (e : Exception){
            e.printStackTrace()
            return false
        }
        return true
    }

    fun onDestory() : Boolean{

        return true
    }

}