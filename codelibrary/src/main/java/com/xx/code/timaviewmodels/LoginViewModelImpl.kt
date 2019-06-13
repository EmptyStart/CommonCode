package com.xx.code.timaviewmodels

import com.xx.code.timaconstracts.ILoginViewModel
import com.xx.common.base.BaseViewModel
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.CommonUrls
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/8/30
 *   email :
 *
 */
class LoginViewModelImpl() : BaseViewModel(),ILoginViewModel{

    override fun addOnVerifyListener(listener: IDataListener) {
        val requestData = listener.requestData()
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executePost(CommonUrls.loginVerify, requestData).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

    override fun addOnLoginListener(listener: IDataListener) {
        val requestData = listener.requestData()
        val baseSubscriber = BaseSubscriber(listener);
        RetrofitHelper.service.executePost(CommonUrls.login,requestData).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

}