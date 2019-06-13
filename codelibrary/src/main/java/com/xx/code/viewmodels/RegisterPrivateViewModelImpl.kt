package com.xx.code.viewmodels

import com.xx.code.constracts.IRegisterPrivateViewModel
import com.xx.common.base.BaseViewModel
import com.xx.common.base.Constant
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
class RegisterPrivateViewModelImpl : BaseViewModel(), IRegisterPrivateViewModel {
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
        RetrofitHelper.service.executePatch(CommonUrls.hrInfo + "${Constant.hrId}/", listener.requestData())
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }
}