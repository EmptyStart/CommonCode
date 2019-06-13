package com.xx.code.views.fragments.manage

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.xx.code.R
import com.xx.code.timaconstracts.IPartTimePresent
import com.xx.code.timaconstracts.IPartTimeView
import com.xx.code.timapresenter.PartTimePresenterImpl
import com.xx.common.base.BaseFragment
import com.xx.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_part_time.*
import kotlinx.android.synthetic.main.code_three_select_top.*
import org.jetbrains.anko.toast

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

        tv_select_one.tag = true
        tv_select_two.tag = false
        tv_select_three.tag = false
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
        loadingManage.show()
    }

    override fun hideLoading() {
        loadingManage.dismiss()
    }

    override fun showError(errorMsg: String) {
        activity.toast(errorMsg)
    }
}