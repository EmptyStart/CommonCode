package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener

/**
 *  兼职
 * Created by Administrator on 2018/8/28/028.
 */
interface IPartTimeViewModel : IBaseViewModel{
    fun addPartTimeListener(listener: IDataListener)
}

interface IPartTimeView : IBaseViews {
    fun getRecyclerPartTimeView() : RecyclerView

    fun getTextSelectOneView() : TextView

    fun getTextSelectTwoView() : TextView

    fun getTextSelectThreeView() : TextView

    fun getPartActivity() : Activity
}
interface IPartTimePresent : IBasePresenter{
    fun init()

    fun toSelect(position : Int)

    fun refreshPublishedAdapter()

    fun refreshAuditAdapter()

    fun refreshAlreadyDownAdapter()
}