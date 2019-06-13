package com.xx.code.viewmodels

import com.xx.code.constracts.IMineViewModel
import com.xx.common.base.BaseViewModel
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.CommonUrls
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

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