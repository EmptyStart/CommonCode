package com.xx.code.constracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener

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