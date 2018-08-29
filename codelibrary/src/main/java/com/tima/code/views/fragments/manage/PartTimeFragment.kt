package com.tima.code.views.fragments.manage

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.timaconstracts.IPartTimePresent
import com.tima.code.timaconstracts.IPartTimeView
import com.tima.code.timapresenter.PartTimePresenterImpl
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_part_time.*
import kotlinx.android.synthetic.main.code_three_select_top.*

/**
 * 管理-兼职
 * Created by Administrator on 2018/8/28/028.
 */
@Route(path = RoutePaths.manageptfragment)
class PartTimeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, IPartTimeView
                        , View.OnClickListener {

    var partTimePresent : IPartTimePresent? = null

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_part_time
    }

    override fun initView() {
        partTimePresent = PartTimePresenterImpl(this)
        swipe_part_time.setOnRefreshListener(this)
        tv_select_one.setOnClickListener(this)
        tv_select_two.setOnClickListener(this)
        tv_select_three.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        partTimePresent!!.onClick(v)
    }

    override fun lazyLoad() {
    }

    override fun onRefresh() {
        swipe_part_time.isRefreshing = false
    }

    override fun getPartActivity(): Activity {
        return activity
    }

    override fun getRecyclerPartTimeView(): RecyclerView {
        return recycler_part_time
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

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }
}