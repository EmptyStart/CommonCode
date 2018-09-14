package com.tima.common.alipay

import com.alipay.sdk.app.PayTask
import com.tima.common.utils.ActivityManage
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * @author : zhijun.li on 2018/9/14
 *   email : zhijun.li@timanetworks.com
 *
 */
object AlipayUtils {
    fun pay(orderInfo: String, sign: String,listener :PayBackListener) {
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { activity ->
            val payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\""
            Observable.create(ObservableOnSubscribe<String> { emitter ->
                run {
                    val alipay = PayTask(activity)
                    val result = alipay.pay(payInfo, true)
                    emitter.onNext(result)
                }
            })
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe {
                        listener.payBack(it)
                    }
        }

    }
}
interface PayBackListener{
    fun payBack(result: String)
}