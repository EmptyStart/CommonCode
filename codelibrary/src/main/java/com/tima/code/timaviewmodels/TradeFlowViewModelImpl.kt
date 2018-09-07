package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.ITradeFlowViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.HttpRequest
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * Created by Administrator on 2018/9/1/001.
 */
class TradeFlowViewModelImpl : BaseViewModel(),ITradeFlowViewModel {
    override fun addTradeFlowListener(listener: IDataListener, url: String, type : HttpRequest) {
        val baseSubscriber = BaseSubscriber(listener)
        if (type == HttpRequest.GET) {
            RetrofitHelper.service.executeGet(url)
                    .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        }else if (type == HttpRequest.POST){
            RetrofitHelper.service.executePost(url,listener.requestData())
                    .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        }
    }
}