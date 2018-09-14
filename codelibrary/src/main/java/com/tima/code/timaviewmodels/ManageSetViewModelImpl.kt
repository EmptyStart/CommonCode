package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IManageSetModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.HttpRequest
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils
import retrofit2.http.PATCH

/**
 * Created by Administrator on 2018/9/3/003.
 */
class ManageSetViewModelImpl : BaseViewModel(),IManageSetModel{
    override fun addManageSetListener(listener: IDataListener, url : String,request: HttpRequest) {
        val baseSubscriber = BaseSubscriber(listener)
        if (request == HttpRequest.GET){
            RetrofitHelper.service.executeGet(url,listener.requestData()).compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        }else if (request == HttpRequest.PATCH){
            RetrofitHelper.service.executePatch(url,listener.requestData()).compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        }

        addSubscription(baseSubscriber)

    }
}