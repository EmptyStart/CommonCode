package com.xx.code.viewmodels

import com.xx.code.constracts.IWalletViewModel
import com.xx.common.base.BaseViewModel
import com.xx.common.base.IDataListener
import com.xx.common.https.BaseSubscriber
import com.xx.common.https.HttpRequest
import com.xx.common.https.RetrofitHelper
import com.xx.common.rx.SchedulerUtils

/**
 * 钱包-界面
 * Created by jjy on 2018/9/7.
 */
class WalletViewModelImpl :  BaseViewModel(), IWalletViewModel {
    override fun addOnBodyDataListener(listener: IDataListener,url : String,requet : HttpRequest) {
        val baseSubscriber = BaseSubscriber(listener)
        if (requet.equals(HttpRequest.GET)){
            RetrofitHelper.service.executeGet(url).compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        }

        addSubscription(baseSubscriber)
    }
}