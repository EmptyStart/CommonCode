package com.tima.code.timaviewmodels

import android.database.Observable
import com.tima.code.timaconstracts.ILoginViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/8/30
 *   email :
 *
 */
class AlipayViewModelImpl() : BaseViewModel(){

    open fun addOnAlipayListener(listener: IDataListener) {
        val requestData = listener.requestData()
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePost(CommonUrls.alipay, requestData).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

}