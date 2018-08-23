package com.tima.code.views.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ColorIdUtil
import kotlinx.android.synthetic.main.code_activity_main.*

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *   公司主页
 */
@Route(path = RoutePaths.company_main)
class CompanyMainActivity : BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int {
        return R.layout.code_activity_main
    }
    override fun onClick(v: View?) {


    }

    var currentPosition: Int = 0

    override fun inits(savedInstanceState: Bundle?) {
        defaultTab()
    }

    fun  haveUnReadMessage(unRead: Boolean){

    }

    private fun defaultTab() {
        ll_tab5.visibility = View.GONE
        tv_tab1.text = "发布"
        tv_tab2.text = "管理"
        iv_tab1.setImageResource(R.mipmap.code_main_release_nor)
        iv_tab2.setImageResource(R.mipmap.code_main_manage_nor)
        ll_tab1.setOnClickListener(this)
        ll_tab2.setOnClickListener(this)
        ll_tab3.setOnClickListener(this)
        ll_tab4.setOnClickListener(this)
        iv_tab1.tag=false
        iv_tab2.tag=false
        iv_tab3.tag=false
        iv_tab4.tag=false
        tabSelect(0)
    }
    fun tabSelect(position: Int) {
        if (iv_tab1.tag as Boolean) {
            iv_tab1.tag = false
            iv_tab1.setImageResource(R.mipmap.code_main_office_nor)
            tv_tab1.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab2.tag as Boolean) {
            iv_tab2.tag = false
            iv_tab2.setImageResource(R.mipmap.code_main_company_nor)
            tv_tab2.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab3.tag as Boolean) {
            iv_tab3.tag = false
            iv_tab3.setImageResource(R.mipmap.code_main_message_nor)
            tv_tab3.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab4.tag as Boolean) {
            iv_tab4.tag = false
            iv_tab4.setImageResource(R.mipmap.code_main_my_nor)
            tv_tab4.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }

        when (position) {
            0 -> {
                if (!(iv_tab1.getTag() as Boolean)) {
                    iv_tab1.tag = true
                    iv_tab1.setImageResource(R.mipmap.code_main_release_pre)
                    tv_tab1.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
            1 -> {
                if (!(iv_tab2.getTag() as Boolean)) {
                    iv_tab2.tag = true
                    iv_tab2.setImageResource(R.mipmap.code_main_manage_pre)
                    tv_tab2.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
            2 -> {
                if (!(iv_tab3.getTag() as Boolean)) {
                    iv_tab3.tag = true
                    iv_tab3.setImageResource(R.mipmap.code_main_message_pre)
                    tv_tab3.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
            3 -> {
                if (!(iv_tab4.getTag() as Boolean)) {
                    iv_tab4.tag = true
                    iv_tab4.setImageResource(R.mipmap.code_main_my_pre)
                    tv_tab4.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
        }
    }


}