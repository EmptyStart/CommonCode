package com.tima.code.timapresenter

import android.view.View
import com.tima.code.R
import com.tima.code.timaconstracts.IMainPagePresent
import com.tima.code.timaconstracts.IMainPageView
import com.tima.code.timaconstracts.IMainPageViewModel
import com.tima.code.timaviewmodels.MainPageViewModelImpl
import kotlinx.android.synthetic.main.code_activity_main.*

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
class MainPagePresenterImpl : IMainPagePresent{
    var mView: IMainPageView?=null
    var viewModel : IMainPageViewModel?=null

    constructor(view: IMainPageView?){
        this.mView=view
        viewModel=MainPageViewModelImpl()
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when(id){
            R.id.ll_tab1->{
                mView?.tabSelect(0)
            }
            R.id.ll_tab2->{
                mView?.tabSelect(1)

            }
            R.id.ll_tab3->{
                mView?.tabSelect(2)

            }
            R.id.ll_tab4->{
                mView?.tabSelect(3)

            }
        }
    }
}