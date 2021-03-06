package com.xx.code.viewmodels

import com.xx.code.constracts.IPersonnelRosterViewModel
import com.xx.common.base.BaseViewModel
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.HttpRequest
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

/**
 * Created by Administrator on 2018/9/1/001.
 */
class PersonnelRosterViewModelImpl : BaseViewModel(),IPersonnelRosterViewModel {
    override fun addPersonnelRosterListener(listener: IDataListener, url: String, type : HttpRequest) {
        val baseSubscriber = BaseSubscriber(listener)
        if (type == HttpRequest.GET) {
            RetrofitHelper.service.executeGet(url)
                    .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        }else if (type == HttpRequest.POST){
            RetrofitHelper.service.executePost(url,listener.requestData())
                    .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        }
        addSubscription(baseSubscriber)
    }
}