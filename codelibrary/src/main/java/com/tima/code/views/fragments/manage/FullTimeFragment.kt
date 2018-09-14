package com.tima.code.views.fragments.manage

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.timaconstracts.IFullTimePresent
import com.tima.code.timaconstracts.IFullTimeView
import com.tima.code.timapresenter.FullTimePresenterImpl
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_full_time.*
import kotlinx.android.synthetic.main.code_three_select_top.*

/**
 *  管理-全职
 * Created by Administrator on 2018/8/28/028.
 */
@Route(path = RoutePaths.manageftfragment)
class FullTimeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, IFullTimeView
        , View.OnClickListener {

    var fullTimePresent : IFullTimePresent? = null

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_full_time
    }

    override fun initView() {
        fullTimePresent = FullTimePresenterImpl(this)
        swipe_full_time.setOnRefreshListener(this)
        tv_select_one.setOnClickListener(this)
        tv_select_two.setOnClickListener(this)
        tv_select_three.setOnClickListener(this)

        tv_select_one.tag = true
        tv_select_two.tag = false
        tv_select_three.tag = false
    }

    override fun onClick(v: View?) {
        fullTimePresent?.onClick(v)
    }

    override fun lazyLoad() {
    }

    override fun onRefresh() {
        swipe_full_time.isRefreshing = false
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }

    override fun getRecyclerFullTimeView(): RecyclerView {
        return recycler_full_time
    }

    override fun getTextSelectOneView(): TextView {
        return tv_select_one
    }

    override fun getTextSelectTwoView(): TextView {
        return tv_select_two
    }

    override fun getTextSelectThreeView(): TextView {
        return tv_select_three
    }

    override fun getFullActivity(): Activity {
        return activity
    }
}