package com.tima.code.views.fragments

import android.app.FragmentTransaction
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ColorIdUtil
import kotlinx.android.synthetic.main.code_double_select_top.*
import kotlinx.android.synthetic.main.code_fragment_manage.*
/**
 *  管理
 * @author : zhijun.li on 2018/8/27
 *   email : zhijun.li@timanetworks.com
 */
@Route(path = RoutePaths.managefragment)
class ManageFragment : BaseFragment(),View.OnClickListener{
    var partTimeFragment : PartTimeFragment? = null
    var fullTimeFragment : FullTimeFragment? = null

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_manage
    }

    override fun initView() {
        ll_one.setOnClickListener(this)
        ll_two.setOnClickListener(this)

        ll_one.tag = false
        ll_two.tag = false

        tv_one.text = "全职"
        tv_two.text = "兼职"
        tabSelect(0)
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_one->{
                tabSelect(0)
            }
            R.id.ll_two->{
                tabSelect(1)
            }
        }
    }

    fun tabSelect(position: Int) {
        if (ll_one.tag as Boolean) {
            ll_one.tag = false
            tv_one.setTextColor(ColorIdUtil.getColorId(R.color.text_gray))
            line_one.visibility = View.GONE
        }
        if (ll_two.tag as Boolean) {
            ll_two.tag = false
            tv_two.setTextColor(ColorIdUtil.getColorId(R.color.text_gray))
            line_two.visibility = View.GONE
        }
        when(position){
            0->{
                if (!(ll_one.tag as Boolean)) {
                    ll_one.tag = true
                    tv_one.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                    line_one.visibility = View.VISIBLE
                }
            }
            1->{
                if (!(ll_two.tag as Boolean)) {
                    ll_two.tag = true
                    tv_two.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                    line_two.visibility = View.VISIBLE
                }
            }
        }
        changeFragment(position)
    }

    fun changeFragment(position: Int) {
        val transaction = fragmentManager.beginTransaction()
        hideFragment(position, transaction)
        when (position) {
            0 -> {
                if (partTimeFragment == null) {
                    partTimeFragment = ARouter.getInstance().build(RoutePaths.manageptfragment).navigation() as PartTimeFragment
                    transaction.add(R.id.fl_manage_layout, partTimeFragment)

                } else {
                    transaction.show(partTimeFragment)
                }
            }
            1 -> {
                if (fullTimeFragment == null) {
                    fullTimeFragment = ARouter.getInstance().build(RoutePaths.manageftfragment).navigation() as FullTimeFragment
                    transaction.add(R.id.fl_manage_layout, fullTimeFragment)
                } else {
                    transaction.show(fullTimeFragment)
                }
            }
        }
        transaction.commit()
    }

    fun hideFragment(position: Int, transaction: FragmentTransaction) {
        if (position != 0) {
            partTimeFragment?.let {
                transaction.hide(it)
            }
        }
        if (position != 1) {
            fullTimeFragment?.let {
                transaction.hide(it)
            }
        }
    }
}