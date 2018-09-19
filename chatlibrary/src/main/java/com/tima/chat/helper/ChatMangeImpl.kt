package com.tima.chat.helper

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.tima.chat.Listener.MyEMMessageListener
import com.tima.common.base.RoutePaths
import com.tima.common.services.IChatManage
import com.tima.common.utils.LogUtils
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/9/19
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.chatManager)
class ChatMangeImpl : IChatManage {
    var context :Context?=null
    override fun init(context: Context?) {
       this.context=context
    }

    override fun initChat(): Boolean {
        try {
            val options = EMOptions()
            options.acceptInvitationAlways = false
            //初始化
            EMClient.getInstance().init(context, options)
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(true)
            EMClient.getInstance().chatManager().addMessageListener(MyEMMessageListener())

            /* val options = ChatClient.Options()
             //初始化
             ChatClient.getInstance().init(context, options)
             //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
             ChatClient.getInstance().setDebugMode(true)
             ChatClient.getInstance().chatManager().addMessageListener(MyMessageListener())*/
            LogUtils.i(ChatUtils.TAG,"初始化聊天信息成功!")
        }catch (e : Exception){
            e.printStackTrace()
            LogUtils.i(ChatUtils.TAG,"初始化聊天信息失败!")
            return false
        }
        return true
    }

    override fun login(name: String, password: String): Boolean {
        var result = false
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            return result
        }
        EMClient.getInstance().login(name, password, object : EMCallBack {
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                LogUtils.i(ChatUtils.TAG,"环信登陆成功——---"+name)
                /*activity.runOnUiThread(Runnable {
                    activity.startActivity(Intent(activity, ChatActivity::class.java))
                    activity.finish()
                })*/
                result = true
            }

            override fun onProgress(progress: Int, status: String) {
            }

            override fun onError(code: Int, message: String) {
                LogUtils.i(ChatUtils.TAG,"环信登陆失败——---"+name+"  报错信息=="+message)
                if (!TextUtils.isEmpty(message) && message.equals("User is already login")){
                    val activity = context as? Activity
                    activity?.let {
                        ChatUtils.loginOut(it)
                    }
                }
                result = false
            }
        })
        return result
    }

    override fun loginOut(): Boolean {
        var result  = true
        EMClient.getInstance().logout(true, object : EMCallBack {

            override fun onSuccess() {
                result = true
                (context as? Activity)?.runOnUiThread {
                    //activity.toast("您已退出登录")
                    LogUtils.i(ChatUtils.TAG,"退出环信成功")
                }
            }

            override fun onProgress(progress: Int, status: String) {}

            override fun onError(code: Int, message: String) {
                result = false
                (context as? Activity)?.runOnUiThread {
                    //activity.toast("退出环信失败")
                    LogUtils.i(ChatUtils.TAG,"退出环信失败")
                }
            }
        })
        return result
    }
}