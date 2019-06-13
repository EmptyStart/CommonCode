package com.xx.chat.ui.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.hyphenate.chat.EMMessage
import com.xx.chat.R
import com.xx.chat.apdater.ChatAdapter
import com.xx.chat.apdater.ChoiceFunctionAdapter
import com.xx.chat.constracts.IChatPresent
import com.xx.chat.constracts.IChatView
import com.xx.chat.helper.ChatUtils
import com.xx.chat.helper.ChatUtils.TYPE_SEND
import com.xx.chat.ui.model.ChatViewModelImpl
import com.xx.chat.weight.RecordVoiceBtnController
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener
import com.xx.common.base.MsgInfo
import com.xx.common.https.ExceptionDeal
import com.xx.common.utils.*
import org.jetbrains.anko.toast
import org.litepal.LitePal

/**
 * Created by Administrator on 2018/8/22/022.
 */
class ChatPersenterImpl : IChatPresent, ChoiceFunctionAdapter.OnChoiceClickListener ,RecordVoiceBtnController.AudioFinishRecorderListener{
    var TAG = "ChatPersenterImpl"

    var view: IChatView? = null
    var chatAdapter : ChatAdapter? = null
    var viewMode: ChatViewModelImpl? = null
    var choiceAdpater: ChoiceFunctionAdapter? = null
    var msgInfos = ArrayList<MsgInfo>()                                                             //聊天信息
    var chatActivity : AppCompatActivity? = null


    constructor(view: IBaseViews?) {
        this.view = view as IChatView?
        viewMode = ChatViewModelImpl()
    }


    override fun init() {
        if (view != null){
            chatActivity = view?.getActivity()
            initChat()
        }
        var infos = LitePal.where("from=?",view?.getFRIENDID()).find(MsgInfo::class.java)
        msgInfos.addAll(infos)
        refreshChatAdapter()
        view?.getVoiceBtView()?.initConv(chatActivity!!, chatAdapter!!)
        view?.getVoiceBtView()?.setAudioFinishRecorderListener(this)
    }

    /**
     * 初始化环信聊天
     */
    fun initChat(){
       /* if(!ChatUtils.isConnected()){
            var result = ChatUtils.init(chatActivity!!)
            if (result) {
                var result = ChatUtils.login(NAME, PASSWORD, chatActivity!!)
                if (!result){
                    chatActivity?.toast("登陆环信失败")
                }
            }else{
                chatActivity?.toast("初始化环信失败")
            }
        }*/
    }

    override fun onRefreshChatAdapter(msgInfos: ArrayList<MsgInfo>) {
    }

    override fun startVoice() {
    }

    override fun stopVoice() {
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_send -> {
                var str = this.view!!.getEdInputSmsView().text.toString()
                sendMsg(str,EMMessage.Type.TXT)
                this.view!!.getEdInputSmsView().setText("")
            }
            R.id.iv_add -> {
                //showGvAddChat()
            }
            R.id.iv_voice -> {
                var isVoice = this.view?.getVoiceBtView()?.visibility == View.VISIBLE
                if (isVoice){                   //隐藏语音
                    this.view?.getVoiceBtView()?.visibility = View.GONE
                    
                }else{                          //显示语音
                    KeyboardUtils.hideSoftInput(chatActivity!!)
                    this.view?.getVoiceBtView()?.visibility = View.VISIBLE
                }
            }
            R.id.reycler_chat -> {
                this.view!!.getGvAddChatView().visibility = View.GONE
            }
            R.id.iv_actionbar_cancle->{
                chatActivity?.finish()
            }
        }
    }

    override fun showGvAddChat() {
        view!!.getGvAddChatView().visibility = View.VISIBLE
        refreshChoiceAdapter()
    }

    override fun sendMsg(content: String, msgType: EMMessage.Type) {
        var message = ChatUtils.sendMsgTxt(content,view?.getFRIENDID()!!)
        if (message != null){
            var msgInfo = MsgInfo()
            msgInfo.content = content
            msgInfo.acceptTime = DateUtils.getDateYMDHMS(System.currentTimeMillis())
            msgInfo.msgType = msgType
            msgInfo.type = TYPE_SEND
            msgInfo.from = view?.getFRIENDID()!!
            msgInfo.save()
            msgInfos.add(msgInfo)

            LogUtils.i(TAG,"--发送--"+view?.getFRIENDID()+"--消息--"+msgInfo.toString())
            refreshChatAdapter()
        }else{
            if (chatActivity != null)
                Toast.makeText(chatActivity,"发送消息失败!",Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 刷新聊天适配器
     */
    fun refreshChatAdapter(){
        if (chatActivity != null) {
            msgInfos.addAll(ChatUtils.msgInfos)
            ChatUtils.msgInfos.clear()

            if (chatAdapter == null) {
                chatAdapter = ChatAdapter(chatActivity!!, msgInfos)
                view!!.getChatRecyclerView().layoutManager = LinearLayoutManager(chatActivity!!,LinearLayoutManager.VERTICAL,false)
                view!!.getChatRecyclerView().scrollToPosition(msgInfos.size-1)
                view!!.getChatRecyclerView().adapter = chatAdapter
            } else {
                view!!.getChatRecyclerView().scrollToPosition(msgInfos.size-1)
                chatAdapter!!.notifyDataSetChanged()
            }
        }else{

        }
    }

    /**
     * 刷新拍照等适配器
     */
    fun refreshChoiceAdapter() {
        if (chatActivity != null) {
            if (choiceAdpater == null) {
                choiceAdpater = ChoiceFunctionAdapter(chatActivity!!, this)
                view!!.getGvAddChatView().setAdapter(choiceAdpater)
                view!!.getGvAddChatView().setNumColumns(4)
            } else {
                choiceAdpater!!.notifyDataSetChanged()
            }
        }else{

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        view?.showLoading()
        viewMode?.addOnBodyDataListener(object : IDataListener {
            override fun successData(success: String) {
                //成功返回
                view?.hideLoading()
            }

            override fun errorData(error: String) {
                //错误返回
                view?.hideLoading()
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                //请求参数
                return null
            }

        })
    }

    /**
     * 选择拍照等功能
     */
    override fun onClickItem(name: String) {
        if (view != null) {
            if (name.equals("拍摄")) {
                var imagePath = CameraUtils.onCallCamera(view!!.getActivity())
            } else {
                CameraUtils.onCallAlbum(view!!.getActivity())
            }
        } else {

        }
    }

    /**
     * 录音结束
     */
    override fun onFinish(seconds: Float, filePath: String) {
        var message = ChatUtils.sendMsgVoice(filePath, seconds.toInt(), view?.getFRIENDID()!!)
        if (message != null) {
            var msgInfo = MsgInfo()
            msgInfo.content = filePath
            msgInfo.acceptTime = DateUtils.getDateYMDHMS(System.currentTimeMillis())
            msgInfo.msgType = EMMessage.Type.VOICE
            msgInfo.type = TYPE_SEND
            msgInfo.from = view?.getFRIENDID()!!
            msgInfo.voiceLeght = seconds.toInt()
            msgInfos.add(msgInfo)
            LogUtils.i(TAG, "--发送--" + view?.getFRIENDID()!! + "--消息--" + msgInfo.toString())
            refreshChatAdapter()
        }else{
            chatActivity?.toast("发送语言失败")
        }
    }
}