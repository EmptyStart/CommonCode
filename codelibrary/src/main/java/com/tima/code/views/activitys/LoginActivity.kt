package com.tima.code.views.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.timaconstracts.ILoginView
import com.tima.code.timapresenter.LoginPresenterImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import org.jetbrains.anko.toast

import kotlinx.android.synthetic.main.code_activity_login.*
/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.login)
class LoginActivity : BaseActivity() ,ILoginView,View.OnClickListener{
    override fun onClick(v: View?) {
        mPresent.onClick(v)
    }

    val mPresent by lazy(LazyThreadSafetyMode.NONE) {
        LoginPresenterImpl(this)
    }
    override fun showLoading() {


    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun getLayoutId(): Int {

        return R.layout.code_activity_login
    }

    override fun inits(savedInstanceState: Bundle?) {
        tv_vaild.setOnClickListener(this)
    }

}