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
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tima.code.R
import com.tima.code.timaconstracts.IWelcomePresent
import com.tima.code.timaconstracts.IWelcomeView
import com.tima.code.timapresenter.WelcomePresenterImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import com.tima.common.utils.LogUtils
import com.tima.common.utils.SpHelper
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.code_activity_welcome.*

/**
 * @author : zhijun.li on 2018/8/22
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.welcome)
class WelcomeActivity : BaseActivity(), IWelcomeView ,View.OnClickListener{
    override fun location() {
        val rxPermissions=RxPermissions(this)
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(object : Consumer<Boolean>{
                    override fun accept(t: Boolean?) {

                    }

                })
        val mLocationClient : AMapLocationClient =AMapLocationClient(application)
        mLocationClient.setLocationListener(object :AMapLocationListener{
            override fun onLocationChanged(p0: AMapLocation?) {
               if (p0?.errorCode==0){
                    LogUtils.i("loc",p0?.address)
               }else{
                    LogUtils.i("loc",p0?.errorCode.toString()+","+p0?.errorInfo)
               }
            }
        })
        val mLocationOption=AMapLocationClientOption()
        mLocationOption.setOnceLocation(true)
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
        mLocationClient.setLocationOption(mLocationOption)
        mLocationClient.startLocation()
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

        tv_hello.setOnClickListener(this)
        tv_hello.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        tv_hello.gravity=Gravity.END and Gravity.CENTER
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }

    override fun useEventBus(): Boolean = false
}