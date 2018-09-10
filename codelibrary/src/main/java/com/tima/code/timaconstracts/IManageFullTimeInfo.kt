package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener

/**
 * Created by Administrator on 2018/8/29/029.
 */
interface IManageFullTimeInfoModel : IBaseViewModel{
    fun addFullTimeListener(listener: IDataListener,url : String)
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

    fun getRecyclerJobDescribeView() : RecyclerView

    fun getRecyclerRequireView() : RecyclerView

    fun getInfoActivity() : Activity

    fun getPositionId() : Int
}
interface IManageFullTimeInfoPresent : IBasePresenter{
    fun onRefreshJobAdapter()

    fun onRefreshRequireAdapter()
}