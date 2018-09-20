package com.tima.code.views.activitys

import android.Manifest
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tima.code.R
import com.tima.code.timaconstracts.IWelcomePresent
import com.tima.code.timaconstracts.IWelcomeView
import com.tima.code.timapresenter.WelcomePresenterImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.IAMapLocationSuccessListener
import com.tima.common.utils.LogUtils
import com.tima.common.utils.MapGaoDe
import com.tima.common.utils.SpHelper
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.code_activity_welcome.*
import org.jetbrains.anko.toast

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