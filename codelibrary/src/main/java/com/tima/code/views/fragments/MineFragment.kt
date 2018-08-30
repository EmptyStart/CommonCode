package com.tima.code.views.fragments

import android.app.Activity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.timaconstracts.IMinePresent
import com.tima.code.timaconstracts.IMineView
import com.tima.code.timapresenter.MinePresenterImpl
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_mine.*

/**
 * 我的-界面
 * @author : zhijun.li on 2018/8/27
 *   email : zhijun.li@timanetworks.com
 */
@Route(path = RoutePaths.minefragment)
class MineFragment : BaseFragment() , View.OnClickListener,IMineView{

    var minePresent : IMinePresent? = null
    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_mine
    }

    override fun initView() {
        minePresent = MinePresenterImpl(this)

        ll_help.setOnClickListener(this)
        tv_phone.setOnClickListener(this)
        ll_shell.setOnClickListener(this)
        ll_wallet.setOnClickListener(this)
        ll_resume.setOnClickListener(this)
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        minePresent?.onClick(v)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }

    override fun getMineActivity() : Activity {
        return activity
    }
}