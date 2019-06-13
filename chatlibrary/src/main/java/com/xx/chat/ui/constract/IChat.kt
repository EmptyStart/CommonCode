package com.xx.chat.constracts

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.GridView
import com.hyphenate.chat.EMMessage
import com.xx.chat.weight.RecordVoiceBtnController
import com.xx.common.base.*

/**
 * Created by Administrator on 2018/8/22/022.
 */
interface IChatViewModel : IBaseViewModel{
    fun addOnBodyDataListener(listener: IDataListener)
}

interface IChatPresent : IBasePresenter{
    fun init()                                                                                      //初始化

    fun onRefreshChatAdapter(msgInfos : ArrayList<MsgInfo>)                                         //刷新聊天界面

    fun startVoice()                                                                                //开始语音

    fun stopVoice()                                                                                 //结束语音

    fun showGvAddChat()                                                                             //显示拍照等 功能

    fun sendMsg(content : String, msgType : EMMessage.Type)                                         //发送消息

}

interface IChatView : IBaseViews{

    fun getGvAddChatView() : GridView

    fun getActivity() : AppCompatActivity

    fun getChatRecyclerView() : RecyclerView

    fun getEdInputSmsView() : EditText

    fun getVoiceBtView() : RecordVoiceBtnController

    fun getFRIENDID() : String                                                                      //聊天对象Id
}