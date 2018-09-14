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
    fun pay(orderInfo: String, sign: String) {
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { it ->
            val payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\""
            Observable.create(ObservableOnSubscribe<String> { emitter ->
                run {
                    val alipay = PayTask(it)
                    val result = alipay.pay(payInfo, true)
                    emitter.onNext(result)
                }
            })
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe {

                    }
        }

    }
}