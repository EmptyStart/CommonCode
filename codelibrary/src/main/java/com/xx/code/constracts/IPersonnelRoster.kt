package com.xx.code.constracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener
import com.xx.common.https.HttpRequest

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