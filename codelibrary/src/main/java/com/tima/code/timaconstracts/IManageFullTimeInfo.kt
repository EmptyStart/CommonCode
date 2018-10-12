package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.maps.model.LatLng
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener

/**
 * Created by Administrator on 2018/8/29/029.
 */
interface IManageFullTimeInfoModel : IBaseViewModel{
    fun addFullTimeListener(listener: IDataListener,url : String)

    /**
     * 配置信息查询
     */
    fun addConfigInfo(listener: IDataListener)
}

interface IManageFullTimeInfoView : IBaseViews {
    fun getManageOneView() : LinearLayout

    fun getManageTwoView() : LinearLayout

    fun getManageThreeView() : LinearLayout

    fun getManageFourView() : LinearLayout

    fun getManageTitleOneView() : TextView

    fun getManageTitleTwoView() : TextView

    fun getManageTitleThreeView() : TextView

    fun getManageTitleFourView() : TextView

    fun getManageNumOneView() : TextView

    fun getManageNumTwoView() : TextView

    fun getManageNumThreeView() : TextView

    fun getManageNumFourView() : TextView

    fun getSalaryView() : TextView

    fun getRecyclerJobDescribeView() : RecyclerView

    fun getTextRequireView() : TextView

    fun setTextAddressTime(province: String?, city: String?, region: String?, address: String?, time: String?)

    fun setTextCompanyInfo(name : String?,skill_set : String?)

    fun getInfoActivity() : Activity

    fun getPositionId() : Int

    fun getManageTypeInt() : Int

    fun setAddressLocation(it: LatLng?)
}
interface IManageFullTimeInfoPresent : IBasePresenter{
    fun onRefreshJobAdapter()

}