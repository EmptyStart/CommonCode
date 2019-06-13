package com.xx.code.views.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.common.base.BaseActivity
import com.xx.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_register_company.*

/**
 * @author : zhijun.li on 2018/9/4
 *   email :
 *
 */
@Route(path = RoutePaths.registerCompany)
class RegisterCompanyActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_my_company->{
                ARouter.getInstance().build(RoutePaths.createcompany).navigation()
            }
        }
    }

    override fun getLayoutId(): Int {

        return R.layout.code_activity_register_company
    }

    override fun inits(savedInstanceState: Bundle?) {
        actionbar.setOnRightTextListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                ARouter.getInstance().build(RoutePaths.mainpage).navigation()
                finish()
            }

        })
        rl_my_company.setOnClickListener(this)
    }

}