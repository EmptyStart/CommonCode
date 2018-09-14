package com.tima.common.alipay

import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.tima.common.utils.ActivityManage
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/9/14
 *   email : zhijun.li@timanetworks.com
 *
 */
object AlipayUtils {
    fun pay(orderInfo: String, sign: String, listener: PayBackListener?) {
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { activity ->
            val payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\""
            Observable.create(ObservableOnSubscribe<Map<String, String>> { emitter ->
                run {
                    val alipay = PayTask(activity)
                    val result = alipay.payV2(payInfo, true)
                    emitter.onNext(result)
                }
            })
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe {
                        //                        PayResult(it).getResult();// 同步返回需要验证的信息
                        val resultStatus = PayResult(it).getResultStatus()
                        if (TextUtils.equals(resultStatus, "9000")) {
                            activity.toast("支付成功")
                        } else {
                            if (TextUtils.equals(resultStatus, "8000")) {
                                activity.toast("支付结果确认中")
                            } else {
                                activity.toast("支付失败")
                            }
                        }
                        listener?.payBack(resultStatus)
                    }
        }

    }
}

interface PayBackListener {
    fun payBack(result: String?)
}