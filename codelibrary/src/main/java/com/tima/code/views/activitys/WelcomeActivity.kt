package com.tima.code.views.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.timaconstracts.IWelcomePresent
import com.tima.code.timaconstracts.IWelcomeView
import com.tima.code.timapresenter.WelcomePresenterImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import com.tima.common.utils.SpHelper
import kotlinx.android.synthetic.main.code_activity_welcome.*

/**
 * @author : zhijun.li on 2018/8/22
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.welcome)
class WelcomeActivity : BaseActivity(), IWelcomeView ,View.OnClickListener{
    override fun onClick(v: View?) {
        var pos: Int by SpHelper("position",0)
        iWelcomePresent?.onClick(v)
    }

    var iWelcomePresent: IWelcomePresent? = null
    override fun getLayoutId(): Int = R.layout.code_activity_welcome

    override fun inits(savedInstanceState: Bundle?) {
        iWelcomePresent = WelcomePresenterImpl(this)
        lifecycle.addObserver(iWelcomePresent as WelcomePresenterImpl)

        tv_hello.setOnClickListener(this)

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }

    override fun useEventBus(): Boolean = false
}