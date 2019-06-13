package com.xx.code.views.activitys

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.xx.code.R
import com.xx.code.timaconstracts.IManageFullTimeInfoPresent
import com.xx.code.timaconstracts.IManagePartTimeInfoView
import com.xx.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_manage_fulltime_info.*
import kotlinx.android.synthetic.main.code_manage_full_four_select_top.*
import org.jetbrains.anko.toast

/**
 * 兼职详情
 * Created by Administrator on 2018/8/30/030.
 */
class ManagePartTimeInfoActivity : BaseActivity(),IManagePartTimeInfoView , View.OnClickListener{

    var manageFullTimeInfoPresent : IManageFullTimeInfoPresent? = null
    override fun getLayoutId(): Int {
        return R.layout.code_activity_manage_fulltime_info
    }

    override fun inits(savedInstanceState: Bundle?) {
        ll_manage_one.setOnClickListener(this)
        ll_manage_two.setOnClickListener(this)
        ll_manage_three.setOnClickListener(this)
        ll_manage_four.setOnClickListener(this)

        ll_manage_one.tag = false
        ll_manage_two.tag = false
        ll_manage_three.tag = false
        ll_manage_four.tag = false
    }

    override fun onClick(v: View?) {
        manageFullTimeInfoPresent?.onClick(v)
    }

    override fun showLoading() {
        loadingBar.show()
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun getManageOneView(): LinearLayout {
        return ll_manage_one
    }

    override fun getManageTwoView(): LinearLayout {
        return ll_manage_two
    }

    override fun getManageThreeView(): LinearLayout {
        return ll_manage_three
    }

    override fun getManageFourView(): LinearLayout {
        return ll_manage_four
    }

    override fun getManageTitleOneView(): TextView {
        return tv_manage_title_one
    }

    override fun getManageTitleTwoView(): TextView {
        return tv_manage_title_two
    }

    override fun getManageTitleThreeView(): TextView {
        return tv_manage_title_three
    }

    override fun getManageTitleFourView(): TextView {
        return tv_manage_title_four
    }

    override fun getManageNumOneView(): TextView {
        return tv_manage_num_one
    }

    override fun getManageNumTwoView(): TextView {
        return tv_manage_num_two
    }

    override fun getManageNumThreeView(): TextView {
        return tv_manage_num_three
    }

    override fun getManageNumFourView(): TextView {
        return tv_manage_num_four
    }

    override fun getRecyclerJobDescribeView(): RecyclerView {
        return recycler_job_describe
    }


    override fun getInfoActivity(): Activity {
        return this
    }
}