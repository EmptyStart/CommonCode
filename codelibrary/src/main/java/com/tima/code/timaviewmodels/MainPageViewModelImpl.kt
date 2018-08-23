package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IMainPageViewModel
import com.tima.common.base.IDataListener
import com.tima.code.timaconstracts.IWelcomeViewModel
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/8/22
 *   email : zhijun.li@timanetworks.com
 *
 */
class MainPageViewModelImpl : IMainPageViewModel {
//    override fun addOnBodyDataListener(listener: IDataListener) {
//        val requestData = listener.requestData()
//        RetrofitHelper.service.executeGet("user/login", requestData).compose(SchedulerUtils
//                .ioToMain()).subscribe(BaseSubscriber(listener))
//    }

}