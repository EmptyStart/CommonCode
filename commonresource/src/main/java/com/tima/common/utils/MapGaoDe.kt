package com.tima.common.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import android.support.v7.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tima.common.base.Constant
import io.reactivex.functions.Consumer

/**
 * @author : lzj on 2018/9/2 20:27
 *         # Email
 */
object MapGaoDe{
    private val TAG="MapGaoDe"
    /**
     * 获取一次定位信息
     */
    fun getLocation(activity : AppCompatActivity,listener: IAMapLocationSuccessListener)  {
        val rxPermissions= RxPermissions(activity)
        val aMapLocation = rxPermissions.requestEach(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        ).subscribe(object : Consumer<Permission> {
            override fun accept(permission: Permission?) {
                permission?.let {
                    if (it.granted) {
                        // 用户已经同意该权限
                        LogUtils.i(TAG, "同意")
                        val mLocationClient: AMapLocationClient = AMapLocationClient(activity.application)
                        mLocationClient.setLocationListener(object : AMapLocationListener {
                            override fun onLocationChanged(p0: AMapLocation?) {
                                if (p0?.errorCode == 0) {
                                    LogUtils.i(TAG, p0?.address)
                                    Constant.latitude = p0.latitude
                                    Constant.longitude = p0.longitude
                                    Constant.locAddress=p0.address
                                    listener.onLocationChanged(p0)
                                } else {
                                    LogUtils.i(TAG, p0?.errorCode.toString() + "," + p0?.errorInfo)
                                }
                            }
                        })
                        val mLocationOption = AMapLocationClientOption()
                        mLocationOption.setOnceLocation(true)
                        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
                        mLocationClient.setLocationOption(mLocationOption)
                        mLocationClient.startLocation()
                    } else if (it.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        LogUtils.i(TAG, "不同意，但是未点击不再询问")

                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        LogUtils.i(TAG, "拒绝了")
                    }
                }

            }
        })
    }
}
interface IAMapLocationSuccessListener{
    fun onLocationChanged(p0: AMapLocation?)
}