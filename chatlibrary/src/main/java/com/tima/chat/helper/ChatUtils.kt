package com.tima.chat.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.hyphenate.EMCallBack
import com.hyphenate.chat.*
import com.tima.chat.Listener.MyEMMessageListener
import com.tima.chat.Listener.MyMessageListener
import com.tima.chat.R
import com.tima.chat.bean.MsgInfo
import com.tima.common.utils.LogUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.hyphenate.chat.EMClient
import com.hyphenate.helpdesk.easeui.recorder.MediaManager
import com.hyphenate.helpdesk.easeui.widget.AlertDialog


/**
 * Created by Administrator on 2018/8/21/021.
 */
object ChatUtils{
    var TAG = "ChatUtils"
    val TYPE_RECEIVED = 0                                           //消息的类型:接收
    val TYPE_SEND = 1                                               //消息的类型:发送
    var msgInfos = ArrayList<MsgInfo>()                             //接受到----聊天信息

    val FRIENDID = "test1"                                          //发送人Id
    val NAME = "test2"                                              //账号
    val PASSWORD = "123456"                                         //密码
    var REFRESH_CHAT = "refresh_chat"                               //刷新聊天信息
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

           /* val options = ChatClient.Options()
            //初始化
            ChatClient.getInstance().init(context, options)
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            ChatClient.getInstance().setDebugMode(true)
            ChatClient.getInstance().chatManager().addMessageListener(MyMessageListener())*/
            LogUtils.i(TAG,"初始化聊天信息成功!")
        }catch (e : Exception){
            e.printStackTrace()
            LogUtils.i(TAG,"初始化聊天信息失败!")
            return false
        }
        return true
    }

    /**
     * 登陆环信
     */
    fun login(name : String,password : String, activity : Activity) : Boolean{
        var result = false
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            return result
        }
        EMClient.getInstance().login(name, password, object : EMCallBack {
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                LogUtils.i(TAG,"登陆成功——---"+name)
                /*activity.runOnUiThread(Runnable {
                    activity.startActivity(Intent(activity, ChatActivity::class.java))
                    activity.finish()
                })*/
                result = true
            }

            override fun onProgress(progress: Int, status: String) {
            }

            override fun onError(code: Int, message: String) {
                LogUtils.i(TAG,"登陆失败——---"+name+"  message=="+message)
                //Toast.makeText(activity,"登陆失败---"+message,Toast.LENGTH_SHORT).show()
                if (!TextUtils.isEmpty(message) && message.equals("User is already login")){
                    loginOut(activity)
                }
                result = false
            }
        })
        return result
    }

    /**
     *  发送消息 语音
     */
    fun sendMsgVoice(content : String,length: Int,friendId : String) : EMMessage?{
        var message : EMMessage
        try {
            message = EMMessage.createVoiceSendMessage(content,length, friendId)
            EMClient.getInstance().chatManager().sendMessage(message)
        }catch (e : Exception){
            e.printStackTrace()
            return null
        }
        return message
    }

    /**
     *  发送消息 语音
     */
    fun sendMsgTxt(content : String,friendId : String) : EMMessage?{
        var message : EMMessage
        try {
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            message = EMMessage.createTxtSendMessage(content, friendId)
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message)
        }catch (e : Exception){
            e.printStackTrace()
            return null
        }
        return message
    }

    fun onDestory() : Boolean{
        EMClient.getInstance().chatManager().removeMessageListener(MyEMMessageListener())
        return true
    }

    /**
     * 退出登录
     */
    fun loginOut(activity: Activity) {
        EMClient.getInstance().logout(true, object : EMCallBack {

            override fun onSuccess() {
                activity.runOnUiThread { Toast.makeText(activity, "您已退出登录！", Toast.LENGTH_SHORT).show() }
            }

            override fun onProgress(progress: Int, status: String) {}

            override fun onError(code: Int, message: String) {
                activity.runOnUiThread { Toast.makeText(activity, "退出失败，" + message, Toast.LENGTH_SHORT).show() }
            }
        })
    }


    /**
     * 上传文件
     */
    fun uploadFile(filePath: String) {
        if (TextUtils.isEmpty(filePath)) {
            return
        }
        val file = File(filePath)
        if (!file.exists()) {
            return
        }

        val service = FileUploadManager.retrofit().create(FileUploadManager.FileUploadService::class.java)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val call = service.upload(body)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val result = response.body()!!.string().trim { it <= ' ' }
                        val jsonObject = JSONObject(result)
                        val uuid = jsonObject.getJSONArray("entities").getJSONObject(0).getString("uuid")
                        val remoteUrl = String.format("%1\$schatfiles/%2\$s", FileUploadManager.SERVER_URL, uuid)
                        LogUtils.i(TAG,"remoteUrl======"+remoteUrl+"        uuid=="+uuid)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "上次图片Upload Error:" + t.message)
            }
        })
    }

    /**
     * 点击清空聊天记录
     */
    fun emptyHistory(activity: Activity) {
        val msg = "情况聊天记录"
        AlertDialog(activity, null, msg, null, AlertDialog.AlertDialogUser { confirmed, bundle ->
            if (confirmed) {
                MediaManager.release()
                ChatClient.getInstance().chatManager().clearConversation(FRIENDID)
            }
        }, true).show()
    }

}