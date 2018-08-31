package com.tima.common.https

import android.database.Observable
import com.tima.common.base.IDataListener
import com.tima.common.utils.LogUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.ResourceObserver
import io.reactivex.subscribers.ResourceSubscriber
import okhttp3.ResponseBody
import kotlin.properties.Delegates

/**
 * @author : zhijun.li on 2018/8/15
 *   email : zhijun.li@timanetworks.com
 *
 */
class BaseSubscriber(listener : IDataListener) : ResourceObserver<ResponseBody>() {
    val TAG ="HttpUtils"
    var listener : IDataListener by Delegates.notNull()

    init {
        this.listener=listener
    }

    override fun onComplete() {
        LogUtils.i(TAG,"onComplete")
    }


    override fun onStart() {
        super.onStart()
        LogUtils.i(TAG,"start")
    }
    override fun onNext(t: ResponseBody) {
        LogUtils.i(TAG,t.string())
        //这里做共通处理
        if (true) {
            listener.successData(t)
        }else{
            //这里做错误的共通处理
            listener.errorData("error")
        }
    }

    override fun onError(e: Throwable) {
        LogUtils.i(TAG,"error"+e.message)
        //这里做错误的共通处理
        listener.errorData("error")
        e.printStackTrace()
    }

}