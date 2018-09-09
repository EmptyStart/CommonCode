package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IWalletViewModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * 钱包-界面
 * Created by jjy on 2018/9/7.
 */
class WalletViewModelImpl :  BaseViewModel(), IWalletViewModel {
    override fun addOnBodyDataListener(listener: IDataListener) {

        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executeGet(CommonUrls.wallet)
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }
}