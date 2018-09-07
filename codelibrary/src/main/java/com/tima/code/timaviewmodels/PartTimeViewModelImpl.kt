package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IPartTimeViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * Created by Administrator on 2018/8/28/028.
 */
class PartTimeViewModelImpl : BaseViewModel(),IPartTimeViewModel{
    override fun addPartTimeListener(listener: IDataListener) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executeGet(CommonUrls.manageFull, listener.requestData())
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)

    }
}