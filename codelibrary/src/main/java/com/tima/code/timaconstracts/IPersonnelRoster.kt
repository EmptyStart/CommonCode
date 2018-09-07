package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.HttpRequest

/**
 * Created by Administrator on 2018/9/1/001.
 */
interface IPersonnelRosterViewModel : IBaseViewModel {
    fun addPersonnelRosterListener(listener: IDataListener, url: String, type : HttpRequest)

}

interface IPersonnelRosterPresent : IBasePresenter {
    fun init()

    fun onRefreshAdapter()
}

interface IPersonnelRosterView : IBaseViews {
    fun onFinish()

    fun getRecyclerPersonnelView() : RecyclerView

    fun getPersonnelActivity() : Activity

    fun getPositionInt() : Int

    fun getTvTotalMoneyView() : TextView
}