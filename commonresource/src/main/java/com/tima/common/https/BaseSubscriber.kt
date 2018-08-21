package com.tima.common.https

import android.database.Observable
import com.tima.common.utils.LogUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.ResourceObserver
import io.reactivex.subscribers.ResourceSubscriber
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/8/15
 *   email : zhijun.li@timanetworks.com
 *
 */
class BaseSubscriber : ResourceObserver<ResponseBody>() {
    val TAG ="HttpUtils"
    private var d:Disposable?=null
    override fun onComplete() {
        LogUtils.i(TAG,"onComplete")
    }


    override fun onStart() {
        super.onStart()
        LogUtils.i(TAG,"start")
    }
    override fun onNext(t: ResponseBody) {
        LogUtils.i(TAG,t.string())
    }

    override fun onError(e: Throwable) {
        LogUtils.i(TAG,"error")
        e.printStackTrace()
    }

}