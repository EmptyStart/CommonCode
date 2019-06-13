package com.xx.code.timaviewmodels

import com.xx.code.timaconstracts.IReleaseTimeViewModel
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.CommonUrls
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/9/12
 *   email :
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