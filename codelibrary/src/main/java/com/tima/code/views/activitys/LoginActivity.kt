package com.tima.code.views.activitys

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.login)
class LoginActivity : BaseActivity(){
    override fun getLayoutId(): Int {

        return R.layout.code_activity_login
    }

    override fun inits(savedInstanceState: Bundle?) {

    }

}