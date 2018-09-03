package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews

/**
 * Created by Administrator on 2018/9/1/001.
 */
interface IPersonnelRosterViewModel : IBaseViewModel {
}

interface IPersonnelRosterPresent : IBasePresenter {
    fun init()

    fun onRefreshAdapter()
}

interface IPersonnelRosterView : IBaseViews {
    fun onFinish()

    fun getRecyclerPersonnelView() : RecyclerView

    fun getPersonnelActivity() : Activity
}