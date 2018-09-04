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
import retrofit2.HttpException
import kotlin.properties.Delegates

/**
 * @author : zhijun.li on 2018/8/15
 *   email : zhijun.li@timanetworks.com
 *
 */
class BaseSubscriber(listener: IDataListener) : ResourceObserver<ResponseBody>() {
    val TAG = "HttpUtils"
    var listener: IDataListener by Delegates.notNull()

    init {
        this.listener = listener
    }

    override fun onComplete() {
        LogUtils.i(TAG, "请求完成" + System.currentTimeMillis())
    }


    override fun onStart() {
        super.onStart()
        LogUtils.i(TAG, "请求开始" + System.currentTimeMillis())
    }

    override fun onNext(t: ResponseBody) {
        val string = t.string();
        string?.let {
            listener.successData(it)
        }
    }

    override fun onError(e: Throwable) {
        //这里做错误的共通处理
        if (e is HttpException) {
            val errorBody = e.response().errorBody()
            errorBody?.apply {
                listener.errorData(string())
            }
        }
    }

}