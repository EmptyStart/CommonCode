package com.tima.code.views.activitys

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.tima.code.R
import com.tima.code.timaconstracts.IManageFullTimeInfoPresent
import com.tima.code.timaconstracts.IManageFullTimeInfoView
import com.tima.code.timapresenter.ManageFullTimeInfoPresenterImpl
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_manage_fulltime_info.*
import kotlinx.android.synthetic.main.code_manage_full_four_select_top.*
import kotlinx.android.synthetic.main.code_manage_part_three_select_top.*
/**
 * 全职详情-兼职详情
 * Created by Administrator on 2018/8/29/029.
 */
class ManageFullTimeInfoActivity : BaseActivity(),IManageFullTimeInfoView, View.OnClickListener{

    var manageFullTimeInfoPresent : IManageFullTimeInfoPresent? = null
    var manageType  = -1                                                                            //1、全职   2、兼职

    override fun getLayoutId(): Int {
        return R.layout.code_activity_manage_fulltime_info
    }

    override fun inits(savedInstanceState: Bundle?) {
        manageFullTimeInfoPresent = ManageFullTimeInfoPresenterImpl(this)
        manageType = intent.getIntExtra("manageType",-1)

        if (manageType == 1) {
            abv_type2.setTitle("全职详情")
            ll_manage_part.visibility = View.GONE
            ll_manage_one.setOnClickListener(this)
            ll_manage_two.setOnClickListener(this)
            ll_manage_three.setOnClickListener(this)
            ll_manage_four.setOnClickListener(this)

            ll_manage_one.tag = false
            ll_manage_two.tag = false
            ll_manage_three.tag = false
            ll_manage_four.tag = false
        }else if (manageType == 2) {
            abv_type2.setTitle("兼职详情")
            ll_manage_full.visibility = View.GONE
            ll_manage_part_one.setOnClickListener(this)
            ll_manage_part_two.setOnClickListener(this)
            ll_manage_part_three.setOnClickListener(this)

            ll_manage_part_one.tag = false
            ll_manage_part_two.tag = false
            ll_manage_part_three.tag = false
        }
        abv_type2.setOnRightImageListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.iv_actionbar_cancle->{
                finish()
            }
        }
        manageFullTimeInfoPresent?.onClick(v)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }

    override fun getManageOneView(): LinearLayout {
        return if (manageType == 1) ll_manage_one else ll_manage_part_one
    }

    override fun getManageTwoView(): LinearLayout {
        return if (manageType == 1) ll_manage_two else ll_manage_part_two
    }

    override fun getManageThreeView(): LinearLayout {
        return if (manageType == 1) ll_manage_three else ll_manage_part_three
    }

    override fun getManageFourView(): LinearLayout {
        return ll_manage_four
    }

    override fun getManageTitleOneView(): TextView {
        return if (manageType == 1) tv_manage_title_one else tv_manage_part_title_one
    }

    override fun getManageTitleTwoView(): TextView {
        return if (manageType == 1) tv_manage_title_two else tv_manage_part_title_two
    }

    override fun getManageTitleThreeView(): TextView {
        return if (manageType == 1) tv_manage_title_three else tv_manage_part_title_three
    }

    override fun getManageTitleFourView(): TextView {
        return tv_manage_title_four
    }

    override fun getManageNumOneView(): TextView {
        return if (manageType == 1) tv_manage_num_one else tv_manage_part_num_one
    }

    override fun getManageNumTwoView(): TextView {
        return if (manageType == 1) tv_manage_num_two else tv_manage_part_num_two
    }

    override fun getManageNumThreeView(): TextView {
        return if (manageType == 1) tv_manage_num_three else tv_manage_part_num_three
    }

    override fun getManageNumFourView(): TextView {
        return tv_manage_num_four
    }

    override fun getRecyclerJobDescribeView(): RecyclerView {
        return recycler_job_describe
    }

    override fun getRecyclerRequireView(): RecyclerView {
        return recycler_require
    }

    override fun getInfoActivity(): Activity {
        return this
    }
}