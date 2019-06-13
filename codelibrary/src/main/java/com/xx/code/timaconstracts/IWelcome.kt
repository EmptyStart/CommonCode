package com.xx.code.timaconstracts

import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener

/**
 * @author : zhijun.li on 2018/6/28
 *   email :
 *
 */
interface IWelcomeViewModel : IBaseViewModel{
    fun addOnBodyDataListener(listener: IDataListener)
}

interface IWelcomePresent : IBasePresenter

interface IWelcomeView : IBaseViews{

    fun bindAccount()
}