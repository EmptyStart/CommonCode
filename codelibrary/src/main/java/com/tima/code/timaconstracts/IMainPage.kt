package com.tima.code.timaconstracts

import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews

/**
 * @author : zhijun.li on 2018/8/23
 *   email :
 *
 */
interface IMainPageViewModel : IBaseViewModel

interface IMainPageView : IBaseViews{
    fun tabSelect(position: Int)
}
interface IMainPagePresent : IBasePresenter
