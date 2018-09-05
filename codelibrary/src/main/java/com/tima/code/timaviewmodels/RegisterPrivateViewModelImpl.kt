package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IRegisterPrivateViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.Constant
import com.tima.common.base.IDataFileListener
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/9/5
 *   email : zhijun.li@timanetworks.com
 *
 */
class RegisterPrivateViewModelImpl : BaseViewModel(),IRegisterPrivateViewModel {

    override fun addOnUpPicListener(listener: IDataFileListener) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePatch(CommonUrls.companyInfo,listener.requestFileData(),
                listener.requestData()).compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

    override fun addOnUpHrListener(listener: IDataListener) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePatch(CommonUrls.hrInfo+"/${Constant.hrId}/",listener.requestData())
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }
}