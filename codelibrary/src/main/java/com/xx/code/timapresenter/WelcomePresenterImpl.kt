package com.xx.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.xx.code.timaconstracts.IWelcomePresent
import com.xx.code.timaconstracts.IWelcomeView
import com.xx.code.timaviewmodels.WelcomeViewModelImpl
import com.xx.common.base.IBaseViews

/**
 * @author : zhijun.li on 2018/6/28
 *   email :
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

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {

        mViewMode.detachView()
    }

}