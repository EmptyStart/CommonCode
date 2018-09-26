package com.tima.code.timapresenter

import android.app.Activity
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
import com.tima.common.base.Constant
import com.tima.common.base.IBaseViews
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ActivityManage
import org.greenrobot.eventbus.EventBus

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
class WelcomePresenterImpl(view: IBaseViews?) : IWelcomePresent {


    var view: IWelcomeView? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { WelcomeViewModelImpl() }

    init {
        this.view = view as IWelcomeView?
    }


    override fun onClick(view: View?) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner) {
        if (Constant.token.isNotEmpty()) {
            view?.bindAccount()
            ARouter.getInstance().build(RoutePaths.mainpage).navigation()
        } else {
            ARouter.getInstance().build(RoutePaths.login).navigation()
        }
        ActivityManage.instance.exitExcept("LoginActivity")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {

        mViewMode.detachView()
    }

}