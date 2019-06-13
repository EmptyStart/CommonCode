package com.xx.common.base

import android.arch.lifecycle.LifecycleObserver
import android.view.View

/**
 * @author : zhijun.li on 2018/6/29
 *   email :
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
