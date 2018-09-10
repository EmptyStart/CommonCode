package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IManageFullTimeInfoModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/8/22
 *   email : zhijun.li@timanetworks.com
 *
 */
class ManageFullTimeInfoModelImpl : BaseViewModel(), IManageFullTimeInfoModel {
    override fun addFullTimeListener(listener: IDataListener,url : String) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executeGet(url)
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)

    }

}