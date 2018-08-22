package com.tima.code.views

import android.os.Bundle
import com.tima.code.R
import com.tima.code.timaconstracts.IWelcomePresent
import com.tima.code.timaconstracts.IWelcomeView
import com.tima.code.timapresenter.WelcomePersenterImpl
import com.tima.common.base.BaseActivity

/**
 * @author : zhijun.li on 2018/8/22
 *   email : zhijun.li@timanetworks.com
 *
 */
class WelcomeActivity : BaseActivity() ,IWelcomeView{

    var iWelcomePresent : IWelcomePresent? =null
    override fun getLayoutId(): Int = R.layout.code_activity_welcome

    override fun inits(savedInstanceState: Bundle?) {
        iWelcomePresent=WelcomePersenterImpl(this)
        lifecycle.addObserver(iWelcomePresent as WelcomePersenterImpl)

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }

    override fun userEventBus(): Boolean =false
}