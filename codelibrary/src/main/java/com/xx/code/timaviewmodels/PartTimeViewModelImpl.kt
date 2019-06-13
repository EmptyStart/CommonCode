package com.xx.code.timaviewmodels

import com.xx.code.timaconstracts.IPartTimeViewModel
import com.xx.common.base.BaseViewModel
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.CommonUrls
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

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