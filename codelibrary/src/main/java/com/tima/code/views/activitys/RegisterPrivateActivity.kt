package com.tima.code.views.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_register_private.*

/**
 * @author : zhijun.li on 2018/9/4
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.registerPrivate)
class RegisterPrivateActivity : BaseActivity(){
    override fun getLayoutId(): Int {

        return R.layout.code_activity_register_private
    }

    override fun inits(savedInstanceState: Bundle?) {
        actionbar.setOnRightTextListener(View.OnClickListener {
            ARouter.getInstance().build(RoutePaths.mainpage).navigation()
            finish()
        })
    }

}