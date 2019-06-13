package com.xx.code.viewmodels

import com.xx.code.constracts.IManageSetModel
import com.xx.common.base.BaseViewModel
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.HttpRequest
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

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