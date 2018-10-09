package com.tima.code.timaviewmodels

import android.database.Observable
import com.tima.code.timaconstracts.ILoginViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/8/30
 *   email : zhijun.li@timanetworks.com
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