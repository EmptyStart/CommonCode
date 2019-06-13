package com.xx.code.views.activitys

import com.amap.api.location.AMapLocation
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.xx.code.R
import com.xx.common.base.*
import com.xx.common.utils.*
import kotlinx.android.synthetic.main.code_layout_info_address_p.*

/**
 * @author : zhijun.li on 2018/9/7
 *   email :
 *
 */
abstract class AbstractAddressAndMapTwoActivity : BaseActivity() {
    protected var marker: Marker? = null
    var bitmapDescriptor: BitmapDescriptor? = null
    protected var latLngDefault: LatLng? = null

    /**
     * 设置定位点
     */
    protected fun setMarker(latLng: LatLng, title: String?): MarkerOptions {
        if (bitmapDescriptor == null) {
            bitmapDescriptor = ResourceUtil.getBitmapDescriptorFactory(R.mipmap.ic_map)
        }
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.draggable(true)
        markerOptions.icon(bitmapDescriptor)
        markerOptions.isFlat = true
        markerOptions.setInfoWindowOffset(-54, -69)
        return markerOptions
    }

    /**
     * 加入mapView
     */
    override fun setMap(): MapView? {
        return map_view
    }

    /**
     * 使用地图
     */
    override fun useMap(): Boolean {
        return true
    }

    /**
     * 地图定位初始化
     */
    fun defaultLoaction() {
        val aMapLocation = Constant.aMapLocation
        if (aMapLocation == null) {
            location(object : IAMapLocationSuccessListener {
                override fun onLocationChanged(p0: AMapLocation?) {
                    Constant.aMapLocation = p0
                    p0?.let {
                        val latLng = LatLng(it.latitude, it.longitude)
                        setLocation(latLng)
                        latLngDefault = latLng
                    }
                }
            })
            return
        }
        val latLng = LatLng(aMapLocation.latitude, aMapLocation.longitude)
        setLocation(latLng)
        latLngDefault = latLng
    }

    /**
     * 设置定位数据
     */
    protected fun setLocation(it: LatLng?) {
        it?.let {
            marker?.let {
                if (!it.isRemoved) {
                    it.remove()
                }
            }
            marker = aMap.addMarker(setMarker(it, null))
            val newLatLng = CameraUpdateFactory.newLatLng(it)
            aMap.moveCamera(newLatLng)
            aMap.minZoomLevel = 12f
            aMap.maxZoomLevel = 18f
        }
    }


    override fun onDestroy() {
        bitmapDescriptor?.recycle()
        super.onDestroy()
    }
}