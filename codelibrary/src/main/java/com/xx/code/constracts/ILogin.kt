package com.xx.code.constracts

import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener

/**
 * @author : zhijun.li on 2018/6/28
 *   email :
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
    fun bindAccount(mobile: String?)
}