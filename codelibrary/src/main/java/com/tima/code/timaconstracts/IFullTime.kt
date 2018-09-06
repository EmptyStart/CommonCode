package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener

/**
 * Created by Administrator on 2018/8/29/029.
 */
/**
 *  兼职
 * Created by Administrator on 2018/8/28/028.
 */
interface IFullTimeViewModel : IBaseViewModel{
    fun addFullTimeListener(listener: IDataListener)
}

interface IFullTimeView : IBaseViews {
    fun getRecyclerFullTimeView() : RecyclerView

    fun getTextSelectOneView() : TextView

    fun getTextSelectTwoView() : TextView

    fun getTextSelectThreeView() : TextView

    fun getFullActivity() : Activity
}
interface IFullTimePresent : IBasePresenter {
    fun init()

    fun toSelect(position : Int)

    fun refreshPublishedAdapter()

    fun refreshAuditAdapter()

    fun refreshAlreadyDownAdapter()
}