package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IMineViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IBaseDataListener
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * Created by Administrator on 2018/8/30/030.
 */
class MineViewModelImpl : BaseViewModel(),IMineViewModel {
    override fun logout(listener: IDataListener) {
        val requestData = listener.requestData()
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePost(CommonUrls.logout, requestData).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }
}