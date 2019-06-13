package com.xx.code.timaviewmodels

import com.xx.code.timaconstracts.IRegisterCompanyViewModel
import com.xx.common.base.IDataFileListener
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.CommonUrls
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/9/5
 *   email :
 *
 */
open class RegisterCompanyViewModelImpl : ConfigInfoViewModelImpl(), IRegisterCompanyViewModel {
    override fun addOnUpCompanyListener(listener: IDataListener) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePatch(CommonUrls.companyInfo, listener.requestData())
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

    override fun addOnUpPicListener(listener: IDataFileListener) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePatch(CommonUrls.upPhoto, listener.requestFileData
        (), listener.requestType(), listener.requestExt()).compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

    override fun addOnUpHrListener(listener: IDataListener) {
        val baseSubscriber = BaseSubscriber(listener)
//        RetrofitHelper.service.executePatch(CommonUrls.hrInfo + "/${Constant.hrId}/", listener.requestData())
        RetrofitHelper.service.executePatch(CommonUrls.hrInfo + "/6/", listener.requestData())
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }
}