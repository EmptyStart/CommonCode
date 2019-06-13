package com.xx.code.views.activitys

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.maps.model.LatLng
import com.xx.code.R
import com.xx.code.responsebody.Company
import com.xx.code.timaconstracts.IManageFullTimeInfoPresent
import com.xx.code.timaconstracts.IManageFullTimeInfoView
import com.xx.code.timapresenter.ManageFullTimeInfoPresenterImpl
import kotlinx.android.synthetic.main.code_activity_manage_fulltime_info.*
import kotlinx.android.synthetic.main.code_include_company_info.*
import kotlinx.android.synthetic.main.code_layout_info_address_p.*
import kotlinx.android.synthetic.main.code_manage_full_four_select_top.*
import org.jetbrains.anko.toast
import org.litepal.LitePal

/**
 * 全职详情-兼职详情
 * Created by Administrator on 2018/8/29/029.
 */
class ManageFullTimeInfoActivity : AbstractAddressAndMapTwoActivity(),IManageFullTimeInfoView, View.OnClickListener{

    var manageFullTimeInfoPresent : IManageFullTimeInfoPresent? = null
    var manageType  = -1                                                                            //1、全职   2、兼职
    var id : Int = -1                                                                               //

    override fun getLayoutId(): Int {
        return R.layout.code_activity_manage_fulltime_info
    }

    override fun inits(savedInstanceState: Bundle?) {
        manageType = intent.getIntExtra("manageType",-1)
        id = intent.getIntExtra("id",-1)
        manageFullTimeInfoPresent = ManageFullTimeInfoPresenterImpl(this)
        lifecycle.addObserver(manageFullTimeInfoPresent!!)
        ll_manage_one.setOnClickListener(this)
        ll_manage_two.setOnClickListener(this)
        ll_manage_three.setOnClickListener(this)
        ll_manage_four.setOnClickListener(this)

        if (manageType == 1) {
            abv_type2.setTitle("全职详情")
            tv_job_require.text = "全职要求"
        }else{
            abv_type2.setTitle("兼职详情")
            tv_job_require.text = "兼职要求"
            tv_manage_title_two.text = "到场管理"
            tv_manage_title_three.text = "结算管理"
            ll_manage_four.visibility = View.GONE
        }
        abv_type2.setOnRightImageListener(this)

        var company = LitePal.findFirst(Company::class.java)
        if(company != null){
            tv_company_name.text = company.full_name
        }
    }

    override fun onClick(v: View?) {
        manageFullTimeInfoPresent?.onClick(v)
    }

    override fun setAddressLocation(it: LatLng?) {
        setLocation(it)
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

    override fun getSalaryView(): TextView {
        return tv_salary
    }

    override fun getManageOneView(): LinearLayout {
        return ll_manage_one
        //return if (manageType == 1) ll_manage_one else ll_manage_part_one
    }

    override fun getManageTwoView(): LinearLayout {
        return ll_manage_two
        //return if (manageType == 1) ll_manage_two else ll_manage_part_two
    }

    override fun getManageThreeView(): LinearLayout {
        return ll_manage_three
        //return if (manageType == 1) ll_manage_three else ll_manage_part_three
    }

    override fun getManageFourView(): LinearLayout {
        return ll_manage_four
    }

    override fun getManageTitleOneView(): TextView {
        return tv_manage_title_one
        //return if (manageType == 1) tv_manage_title_one else tv_manage_part_title_one
    }

    override fun getManageTitleTwoView(): TextView {
        return tv_manage_title_two
        //return if (manageType == 1) tv_manage_title_two else tv_manage_part_title_two
    }

    override fun getManageTitleThreeView(): TextView {
        return tv_manage_title_three
        //return if (manageType == 1) tv_manage_title_three else tv_manage_part_title_three
    }

    override fun getManageTitleFourView(): TextView {
        return tv_manage_title_four
    }

    override fun getManageNumOneView(): TextView {
        return tv_manage_num_one
       // return if (manageType == 1) tv_manage_num_one else tv_manage_part_num_one
    }

    override fun getManageNumTwoView(): TextView {
        return tv_manage_num_two
        //return if (manageType == 1) tv_manage_num_two else tv_manage_part_num_two
    }

    override fun getManageNumThreeView(): TextView {
        return tv_manage_num_three
        //return if (manageType == 1) tv_manage_num_three else tv_manage_part_num_three
    }

    override fun getManageNumFourView(): TextView {
        return tv_manage_num_four
    }

    override fun getRecyclerJobDescribeView(): RecyclerView {
        return recycler_job_describe
    }

    override fun getTextRequireView(): TextView {
        return tv_require
    }

    override fun getInfoActivity(): Activity {
        return this
    }

    override fun getPositionId(): Int {
        return id
    }

    override fun getManageTypeInt(): Int {
        return manageType
    }

    override fun setTextAddressTime(province: String?, city: String?, region: String?, address: String?, time: String?) {
        if (province != null)
            tv_pro.text = province
        if (city != null)
            tv_city.text = city
        if (region != null)
            tv_county.text = region
        if (address != null)
            tv_address.text = address
        if (time != null)
            tv_work_time.text = time
    }

    override fun setTextCompanyInfo(name: String?, skill_set: String?) {
        var nameType = if (name != null) name else ""
        tv_work_type.text =  nameType + if (manageType == 1) "（全职）" else "（兼职）"
        if (skill_set != null)
            tv_skill_label.text = skill_set
        else
            tv_skill_label.text = ""
    }

}