package com.tima.code.timaconstracts

import android.database.Observable
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
interface ILoginViewModel : IBaseViewModel {
    fun addOnLoginListener(listener: IDataListener)
    fun addOnVerifyListener(listener: IDataListener)
}

interface ILoginPresent : IBasePresenter

interface ILoginView : IBaseViews {
    fun mobile(): String?
    fun valid(): String?
    fun getValidSuccess()
    fun cancelValid()
}