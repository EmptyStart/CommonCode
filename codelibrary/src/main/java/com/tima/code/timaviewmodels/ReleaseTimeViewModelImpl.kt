package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IReleaseTimeViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/9/12
 *   email : zhijun.li@timanetworks.com
 *
 */
class ReleaseTimeViewModelImpl : ConfigInfoViewModelImpl(),IReleaseTimeViewModel {
    override fun addOnRepayListener(listener: IDataListener) {
        val requestData = listener.requestData()
        val baseSubscriber = BaseSubscriber(listener);
        RetrofitHelper.service.executePost(CommonUrls.replayTime,requestData).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

    override fun addOnReleaseTimeListener(listener: IDataListener) {
        val requestData = listener.requestData()
        val baseSubscriber = BaseSubscriber(listener);
        RetrofitHelper.service.executePost(CommonUrls.releaseTime,requestData).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }
}