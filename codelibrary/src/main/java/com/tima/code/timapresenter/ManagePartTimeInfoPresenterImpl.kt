package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.tima.code.timaconstracts.IManagePartTimeInfoPresent
import com.tima.code.timaviewmodels.ManagePartTimeInfoModelImpl

/**
 * Created by Administrator on 2018/8/30/030.
 */
class ManagePartTimeInfoPresenterImpl : IManagePartTimeInfoPresent {
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { ManagePartTimeInfoModelImpl() }
    override fun onClick(view: View?) {
    }

    override fun onRefreshJobAdapter() {
    }

    override fun onRefreshRequireAdapter() {
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}