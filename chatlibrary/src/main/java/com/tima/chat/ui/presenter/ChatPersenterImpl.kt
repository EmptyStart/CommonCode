package com.tima.chat.ui.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hyphenate.chat.EMMessage
import com.tima.chat.R
import com.tima.chat.apdater.ChatAdapter
import com.tima.chat.apdater.ChoiceFunctionAdapter
import com.tima.chat.bean.MsgInfo
import com.tima.chat.constracts.IChatPresent
import com.tima.chat.constracts.IChatView
import com.tima.chat.helper.ChatUtils
import com.tima.chat.helper.ChatUtils.FRIENDID
import com.tima.chat.helper.ChatUtils.NAME
import com.tima.chat.helper.ChatUtils.PASSWORD
import com.tima.chat.helper.ChatUtils.TYPE_SEND
import com.tima.chat.ui.model.ChatViewModelImpl
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.ApiException
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.CameraUtils
import com.tima.common.utils.DateUtils
import com.tima.common.utils.LogUtils
import kotlinx.android.synthetic.main.chat_activity_chat_layout.*
import kotlinx.android.synthetic.main.chat_send_msg_buttom_layout.*
import kotlinx.android.synthetic.main.chat_send_msg_buttom_layout.view.*
import okhttp3.ResponseBody

/**
 * Created by Administrator on 2018/8/22/022.
 */
class ChatPersenterImpl : IChatPresent, ChoiceFunctionAdapter.OnChoiceClickListener {
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
            chatActivity!!.runOnUiThread(Runnable {
                ChatUtils.init(chatActivity!!)
                ChatUtils.login(NAME, PASSWORD, chatActivity!!)
            })
        }
        refreshChatAdapter()
        view?.getVoiceBtView()?.initConv(chatActivity!!, chatAdapter!!)
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
                    this.view?.getVoiceBtView()?.visibility = View.VISIBLE
                }
            }
            R.id.reycler_chat -> {
                this.view!!.getGvAddChatView().visibility = View.GONE
            }
        }
    }

    override fun showGvAddChat() {
        view!!.getGvAddChatView().visibility = View.VISIBLE
        refreshChoiceAdapter()
    }

    override fun sendMsg(content: String, msgType: EMMessage.Type) {
        var result = ChatUtils.sendMsg(content,FRIENDID)
        if (result){
            var msgInfo = MsgInfo()
            msgInfo.content = content
            msgInfo.acceptTime = DateUtils.getDateYMDHMS(System.currentTimeMillis())
            msgInfo.msgType = msgType
            msgInfo.type = TYPE_SEND
            msgInfo.from = FRIENDID
            msgInfos.add(msgInfo)

            LogUtils.i(TAG,ChatUtils.NAME+"--发送--"+ChatUtils.FRIENDID+"--消息--"+msgInfo.toString())
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
            if (chatAdapter == null) {
                chatAdapter = ChatAdapter(chatActivity!!, msgInfos)
                view!!.getChatRecyclerView().layoutManager = LinearLayoutManager(chatActivity!!,LinearLayoutManager.VERTICAL,false)
                view!!.getChatRecyclerView().adapter = chatAdapter
            } else {
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
            override fun successData(success: ResponseBody) {
                //成功返回
                view?.hideLoading()
            }

            override fun errorData(error: String) {
                //错误返回
                view?.hideLoading()
                ExceptionDeal.handleException(ApiException(error))
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
}