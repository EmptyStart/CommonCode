package com.tima.common.base

import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IConstract{
    interface ViewModel : IBaseViewModel
    interface Present : IBasePresenter
    interface View : IBaseViews
}