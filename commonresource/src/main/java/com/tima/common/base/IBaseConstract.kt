package com.tima.common.base

import android.arch.lifecycle.LifecycleObserver
import android.view.View
import io.reactivex.disposables.CompositeDisposable

/**
 * @author : zhijun.li on 2018/6/29
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IBaseViews {
    fun showLoading()

    fun hideLoading()

    fun showError(errorMsg: String)

}
interface IBasePresenter : LifecycleObserver {
    fun onClick(view :View?)
}
interface IBaseViewModel{

}
