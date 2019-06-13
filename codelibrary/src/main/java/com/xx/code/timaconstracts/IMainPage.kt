package com.xx.code.timaconstracts

import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews

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
