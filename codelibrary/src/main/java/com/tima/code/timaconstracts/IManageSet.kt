package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews

/**
 * Created by Administrator on 2018/9/3/003.
 */
interface IManageSetModel : IBaseViewModel

interface IManageSetView : IBaseViews {
    fun getManageActivity() : Activity

    fun getManageSetRecyclerView() : RecyclerView

    fun getTextSelectOneView() : TextView

    fun getTextSelectTwoView() : TextView

    fun getTextSelectThreeView() : TextView
}
interface IManageSetPresent : IBasePresenter{
    fun toSelect(position : Int)

    fun onRefreshAdapter()
}