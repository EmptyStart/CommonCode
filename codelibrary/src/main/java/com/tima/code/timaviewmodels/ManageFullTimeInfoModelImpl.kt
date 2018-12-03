package com.tima.code.timaviewmodels

import com.tima.code.timaconstracts.IConfigInfoViewModel
import com.tima.code.timaconstracts.IManageFullTimeInfoModel
import com.tima.common.base.BaseViewModel
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils

/**
 * @author : zhijun.li on 2018/8/22
 *   email :
 *
 */
class ManageFullTimeInfoModelImpl : BaseViewModel(), IManageFullTimeInfoModel {

    /**
     * CV_ONBOARD 求职意向-到岗时间
     * CV_STATUS 求职意向-状态
     * CV_TYPE 求职意向-类型
     * PROVINCE_VERSION 省-市-区三级信息 返回key当前版本 value 数据地址
     * EXPECTED_SALARY 期望工资列表
     * COMPANY_STAFFS 员工数，取value的值。10代表10+，100代表100+
     * WORK_EXPERIENCE 工作经验
     */
    override fun addConfigInfo(listener: IDataListener) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executeGet(CommonUrls.configInfo, listener.requestData()).compose(SchedulerUtils
                .ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)
    }

    override fun addFullTimeListener(listener: IDataListener,url : String) {
        val baseSubscriber = BaseSubscriber(listener)
        RetrofitHelper.service.executeGet(url)
                .compose(SchedulerUtils.ioToMain()).subscribe(baseSubscriber)
        addSubscription(baseSubscriber)

    }

}