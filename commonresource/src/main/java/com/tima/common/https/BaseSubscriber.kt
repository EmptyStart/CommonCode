package com.tima.common.https

import com.tima.common.base.IBaseDataListener
import com.tima.common.utils.LogUtils
import io.reactivex.observers.ResourceObserver
import okhttp3.ResponseBody
import retrofit2.HttpException
import kotlin.properties.Delegates

/**
 * @author : zhijun.li on 2018/8/15
 *   email :
 *
 */
class BaseSubscriber(listener: IBaseDataListener) : ResourceObserver<ResponseBody>() {
    val TAG = "HttpUtils"
    var listener: IBaseDataListener by Delegates.notNull()

    init {
        this.listener = listener
    }

    override fun onComplete() {
        LogUtils.i(TAG, "请求完成")
    }


    override fun onStart() {
        super.onStart()
        LogUtils.i(TAG, "请求开始")
    }

    override fun onNext(t: ResponseBody) {
        LogUtils.i(TAG, "请求成功")
        val string = t.string();
        string?.let {
            listener.successData(it)
        }
    }

    override fun onError(e: Throwable) {
        LogUtils.i(TAG, "请求失败")
        //这里做错误的共通处理
        if (e is HttpException) {
            val errorBody = e.response().errorBody()
            errorBody?.apply {
                listener.errorData(string())
            }
        }
    }


}