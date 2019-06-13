package com.xx.code.views.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.common.base.BaseActivity
import com.xx.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_register_selector.*

/**
 * @author : zhijun.li on 2018/9/4
 *   email :
 *
 */
@Route(path = RoutePaths.registerSelect)
class RegisterSelectActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register_company->{
                ARouter.getInstance().build(RoutePaths.createcompany).navigation()
            }
            R.id.register_private->{
                ARouter.getInstance().build(RoutePaths.registerPrivate).navigation()
            }
        }
    }

    override fun getLayoutId(): Int {

        return R.layout.code_activity_register_selector
    }

    override fun inits(savedInstanceState: Bundle?) {
        register_company.setOnClickListener(this)
        register_private.setOnClickListener(this)
    }

}