package com.xx.common.alipay

import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.CommonUrls
import com.xx.common.https.ExceptionDeal
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils
import com.xx.common.utils.ActivityManage
import com.xx.common.utils.GsonUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/9/14
 *   email :
 *
 */
object AlipayUtils {


    //传入订单信息起调支付宝
    fun pay(orderInfo: String, listener: PayBackListener?) {
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { activity ->
            //            val payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\""
            Observable.create(ObservableOnSubscribe<Map<String, String>> { emitter ->
                run {
                    val alipay = PayTask(activity)
                    val result = alipay.payV2(orderInfo, true)
                    emitter.onNext(result)
                }
            })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        //                        PayResult(it).getResult();// 同步返回需要验证的信息
                        val resultStatus = PayResult(it).getResultStatus()
                        var result = ""
                        if (TextUtils.equals(resultStatus, "9000")) {
                            result = "支付成功"
                        } else {
                            if (TextUtils.equals(resultStatus, "8000")) {
                                result = "支付结果确认中"
                            } else {
                                result = "支付失败"
                            }
                        }
                        activity.toast(result)
                        listener?.payBack(resultStatus)
                    }
        }

    }

    //传入钱获取订单信息
    fun addOnAlipayListener(listener: PayOrderListener) {
        val money = listener.money()
        if (money.isNullOrEmpty()) return
        val baseSubscriber = BaseSubscriber(object : IDataListener {
            override fun requestData(): Map<String, String>? {
                return null
            }

            override fun successData(success: String) {
                val orderInfo = GsonUtils.getGson.fromJson(success, ProductMyOrderInfo::class.java)
                orderInfo?.apply {
                    if (order_string.isNotEmpty()) {
                        listener.productOrder(order_string)
                        return
                    }
                }
            }

            override fun errorData(error: String) {
                ExceptionDeal.handleException(error)
            }
        })
        RetrofitHelper.service.executePost(CommonUrls.alipay, mapOf(Pair("amount", money!!))).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
    }
}

interface PayOrderListener {
    fun productOrder(orderInfo: String)
    fun money(): String?
}

interface PayBackListener {
    fun payBack(result: String?)
}