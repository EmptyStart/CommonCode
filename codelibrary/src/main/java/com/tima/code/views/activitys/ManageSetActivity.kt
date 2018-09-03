package com.tima.code.views.activitys

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.tima.code.R
import com.tima.code.timaconstracts.IManageSetView
import com.tima.code.timapresenter.ManageSetPresenterImpl
import com.tima.common.base.BaseActivity
import com.tima.common.utils.KeyboardUtils
import kotlinx.android.synthetic.main.code_activity_manage_set.*
import kotlinx.android.synthetic.main.code_three_select_top.*
import org.jetbrains.anko.toast

/**
 * 管理-集合-界面
 * Created by Administrator on 2018/9/3/003.
 */
class ManageSetActivity : BaseActivity(),IManageSetView, View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    var manageSet : ManageSetPresenterImpl? = null
    var manageSetType = -1                                                                          //兼职 1、报名管理 2、到场管理3、结算管理   全职 4、报名管理 5、面试管理 6、入职管理 7、结算管理
    override fun getLayoutId(): Int {
        return R.layout.code_activity_manage_set
    }

    override fun inits(savedInstanceState: Bundle?) {
        manageSetType = intent.getIntExtra("manageSetType",-1)
        setLayoutView()
        manageSet = ManageSetPresenterImpl(this)

        swipe_manage_set.setOnRefreshListener(this)
        iv_search.setOnClickListener(this)
        tv_click_one.setOnClickListener(this)
        tv_click_two.setOnClickListener(this)
        tv_select_one.setOnClickListener(this)
        tv_select_two.setOnClickListener(this)
        abv_type2.setOnRightImageListener(this)
        iv_total_check.setOnClickListener(this)
        tv_select_three.setOnClickListener(this)

        tv_select_one.tag = false
        tv_select_two.tag = false
        tv_select_three.tag = false

        KeyboardUtils.hideSoftInput(this)
        manageSet?.toSelect(0)
    }

    override fun onClick(v: View?) {
        manageSet?.onClick(v)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }

    override fun getManageActivity(): Activity {
        return this
    }

    override fun getManageSetRecyclerView() : RecyclerView {
        return recycler_manage_set
    }

    override fun getTextSelectTwoView(): TextView {
        return tv_select_two
    }

    override fun getTextSelectThreeView(): TextView {
        return tv_select_three
    }

    override fun getTextSelectOneView(): TextView {
        return tv_select_one
    }

    fun setLayoutView(){
        if (manageSetType == 1){        //兼职 1、报名管理
            abv_type2.setTitle("报名管理")
            tv_click_one.text = "同意"
            tv_click_two.text = "拒绝"
            tv_select_one.text = "待审批"
            tv_select_two.text = "已同意"
            tv_select_three.text = "已拒绝"
        }else if (manageSetType == 2){  //兼职 2、到场管理
            abv_type2.setTitle("到场管理")
            tv_click_one.text = "到场"
            tv_click_two.text = "未到场"
            tv_select_one.text = "待确认"
            tv_select_two.text = "已到场"
            tv_select_three.text = "未到场"
        }else if (manageSetType == 3){  //兼职 3、结算管理
            abv_type2.setTitle("结算管理")
            tv_click_one.text = "完成"
            tv_click_two.text = "中途离场"
            tv_select_one.text = "待确认"
            tv_select_two.text = "已完成"
            tv_select_three.text = "中途离场"
        }else if (manageSetType == 4){  //全职 4、报名管理
            abv_type2.setTitle("报名管理")
            tv_click_one.text = "同意"
            tv_click_two.text = "拒绝"
            tv_select_one.text = "待审批"
            tv_select_two.text = "已同意"
            tv_select_three.text = "已拒绝"
        }else if (manageSetType == 5){  //全职 5、面试管理
            abv_type2.setTitle("面试管理")
            tv_click_one.text = "到场"
            tv_click_two.text = "未到场"
            tv_select_one.text = "待确认"
            tv_select_two.text = "已到场"
            tv_select_three.text = "未到场"
        }else if (manageSetType == 6){  //全职 6、入职管理
            abv_type2.setTitle("入职管理")
            tv_click_one.text = "入职邀请"
            tv_click_two.text = "拒绝"
            tv_select_one.text = "待确认"
            tv_select_two.text = "待入职"
            tv_select_three.text = "已拒绝"
        }else if (manageSetType == 7){  //全职 7、结算管理
            abv_type2.setTitle("结算管理")
            tv_click_one.text = "完成"
            tv_click_two.text = "中途离职"
            tv_select_one.text = "待确认"
            tv_select_two.text = "已完成"
            tv_select_three.text = "中途离职"
        }else{
            toast("界面异常")
            finish()
        }
    }

    override fun onRefresh() {
        swipe_manage_set.isRefreshing = false
    }
}