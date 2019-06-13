package com.xx.code.views.activitys

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.code.constracts.IWelcomePresent
import com.xx.code.constracts.IWelcomeView
import com.xx.code.presenter.WelcomePresenterImpl
import com.xx.common.base.BaseActivity
import com.xx.common.base.Constant
import com.xx.common.base.RoutePaths
import com.xx.common.utils.SpHelper
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/22
 *   email :
 *
 */
@Route(path = RoutePaths.welcome)
class WelcomeActivity : BaseActivity(), IWelcomeView ,View.OnClickListener{
    override fun bindAccount() {

    }


    override fun onClick(v: View?) {
        var pos: Int by SpHelper("position",0)
        iWelcomePresent?.onClick(v)
    }

    var iWelcomePresent: IWelcomePresent? = null
    override fun getLayoutId(): Int = R.layout.code_activity_welcome

    override fun inits(savedInstanceState: Bundle?) {
        iWelcomePresent = WelcomePresenterImpl(this)
        lifecycle.addObserver(iWelcomePresent as WelcomePresenterImpl)

        if (Constant.token.isNotEmpty()) {
            if (TextUtils.equals(Constant.companyState,"N")){
                ARouter.getInstance().build(RoutePaths.registerSelect).navigation()
            }else {
//                ARouter.getInstance().build(RoutePaths.registerSelect).navigation()
            ARouter.getInstance().build(RoutePaths.mainpage).navigation()
            }
        } else {
            ARouter.getInstance().build(RoutePaths.login).navigation()
        }
        finish()
    }


    override fun showLoading() {
        loadingBar.show()
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun useEventBus(): Boolean = false


}