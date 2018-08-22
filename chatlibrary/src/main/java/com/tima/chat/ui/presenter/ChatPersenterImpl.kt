package com.tima.chat.ui.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import com.tima.chat.bean.MsgInfo
import com.tima.chat.constracts.IChatPresent
import com.tima.chat.constracts.IChatView
import com.tima.chat.constracts.IChatViewModel
import com.tima.chat.ui.model.ChatViewModelImpl
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.ApiException
import com.tima.common.https.ExceptionDeal
import okhttp3.ResponseBody

/**
 * Created by Administrator on 2018/8/22/022.
 */
class ChatPersenterImpl : IChatPresent {

    var view: IChatView? = null
    var viewMode: ChatViewModelImpl? = null

    constructor(view: IBaseViews?) {
        this.view = view as IChatView?
        viewMode = ChatViewModelImpl()
    }


    override fun onRefreshChatAdapter(msgInfos: ArrayList<MsgInfo>) {
    }

    override fun startVoice() {
    }

    override fun stopVoice() {
    }

    override fun onClick(view: View?) {
        when(view!!.id){

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        Log.i("tag", "onCreate")
//        Super calls to java default methods are prohibited in JVM target 1.6 Recompile with '-jvm-target 1.8'
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

}