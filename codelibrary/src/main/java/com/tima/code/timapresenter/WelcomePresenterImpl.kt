package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.timaconstracts.IWelcomePresent
import com.tima.code.timaconstracts.IWelcomeView
import com.tima.code.timaconstracts.IWelcomeViewModel
import com.tima.code.timaviewmodels.WelcomeViewModelImpl
import com.tima.common.BusEvents.SelectPos1
import com.tima.common.base.IBaseViews
import com.tima.common.base.RoutePaths
import org.greenrobot.eventbus.EventBus

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
class WelcomePresenterImpl : IWelcomePresent {


    var view: IWelcomeView? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { WelcomeViewModelImpl() }

    constructor(view: IBaseViews?) {
        this.view = view as IWelcomeView?
    }


    override fun onClick(view: View?) {
        Log.i("tag", "onclick event")
        ARouter.getInstance().build(RoutePaths.createcompany).navigation()
//        EventBus.getDefault().postSticky(SelectPos1(true))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        Log.i("tag", "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }

}