package com.tima.code.timapresenter

import android.view.View
import com.tima.code.timaconstracts.IMainPagePresent
import com.tima.code.timaconstracts.IMainPageView
import com.tima.code.timaconstracts.IMainPageViewModel
import com.tima.code.timaviewmodels.MainPageViewModelImpl

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
class MainPagePresenterImpl : IMainPagePresent{
    var view: IMainPageView?=null
    var viewModel : IMainPageViewModel?=null

    constructor(view: IMainPageView?){
        this.view=view
        viewModel=MainPageViewModelImpl()
    }
    override fun onClick(view: View?) {

    }
}