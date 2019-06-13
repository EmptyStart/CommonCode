package com.xx.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.xx.code.R
import com.xx.code.timaconstracts.IMainPagePresent
import com.xx.code.timaconstracts.IMainPageView
import com.xx.code.timaviewmodels.MainPageViewModelImpl

/**
 * @author : zhijun.li on 2018/8/23
 *   email :
 *
 */
class MainPagePresenterImpl : IMainPagePresent {
    var mView: IMainPageView? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { MainPageViewModelImpl() }

    constructor(view: IMainPageView?) {
        this.mView = view
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id) {
            R.id.ll_tab1 -> {
                mView?.tabSelect(0)
            }
            R.id.ll_tab2 -> {
                mView?.tabSelect(1)

            }
            R.id.ll_tab3 -> {
                mView?.tabSelect(2)

            }
            R.id.ll_tab4 -> {
                mView?.tabSelect(3)

            }
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}