package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IRegisterCompanyViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataFileListener
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author : zhijun.li on 2018/9/5
 *   email : zhijun.li@timanetworks.com
 *
 */
open class RegisterCompanyViewModelImpl : ConfigInfoViewModelImpl(), IRegisterCompanyViewModel {
    override fun addOnUpCompanyListener(listener: IDataListener) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePatch(CommonUrls.hrInfo + "/6/", listener.requestData())
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