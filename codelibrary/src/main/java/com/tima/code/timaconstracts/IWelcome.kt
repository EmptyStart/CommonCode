package com.tima.code.timaconstracts

import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.utils.IAMapLocationSuccessListener

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IWelcomeViewModel : IBaseViewModel{
    fun addOnBodyDataListener(listener: IDataListener)
}

interface IWelcomePresent : IBasePresenter

interface IWelcomeView : IBaseViews{
}