package com.xx.code.viewmodels

import com.xx.common.base.IDataListener
import com.xx.code.constracts.IWelcomeViewModel
import com.xx.common.base.BaseViewModel
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/8/22
 *   email :
 *
 */
class WelcomeViewModelImpl : BaseViewModel(),IWelcomeViewModel {
    override fun addOnBodyDataListener(listener: IDataListener) {
        val requestData = listener.requestData()
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executeGet("user/login", requestData).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

}