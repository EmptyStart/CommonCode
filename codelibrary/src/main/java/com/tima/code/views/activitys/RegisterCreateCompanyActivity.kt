package com.tima.code.views.activitys

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdate
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.tima.code.R
import com.tima.common.base.BaseActivity
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.LogUtils
import com.tima.common.utils.ResourceUtil
import kotlinx.android.synthetic.main.code_activity_register_create_company.*
import kotlinx.android.synthetic.main.code_layout_select_address.*

/**
 * @author : lzj on 2018/9/2 17:36
 *         # Email
 */
@Route(path = RoutePaths.createcompany)
class RegisterCreateCompanyActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.code_activity_register_create_company
    }

    override fun inits(savedInstanceState: Bundle?) {
        val geocodeSearch = GeocodeSearch(this)
        if (Constant.latitude != -1.0) {
            val latLng = LatLng(Constant.latitude, Constant.longitude)
            aMap.addMarker(defaultMarker(latLng))
            val newLatLng = CameraUpdateFactory.newLatLng(latLng)
            aMap.moveCamera(newLatLng)
            aMap.setOnMarkerDragListener(object : AMap.OnMarkerDragListener{
                override fun onMarkerDragEnd(p0: Marker?) {
                   p0?.let {
                       val position = p0.position
                       val latLonPoint = LatLonPoint(position.latitude, position.longitude)
                       geocodeSearch.setOnGeocodeSearchListener(object : GeocodeSearch
                       .OnGeocodeSearchListener{
                           override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
                               val address = p0?.regeocodeAddress
                               LogUtils.i("map", address.toString())
                           }

                           override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {
                               p0?.let {
                                   it.geocodeAddressList.forEach {
                                       LogUtils.i("map", it.toString())
                                   }
                               }
                           }

                       })
                       val regeocodeQuery = RegeocodeQuery(latLonPoint, 200f, GeocodeSearch.AMAP)
                       geocodeSearch.getFromLocation(regeocodeQuery)
                   }

                }

                override fun onMarkerDragStart(p0: Marker?) {
                    LogUtils.i("map", "start")
                }

                override fun onMarkerDrag(p0: Marker?) {
                    LogUtils.i("map", "Drag")
                }

            })
        }
    }

    override fun setMap(): MapView? {
        return map_view
    }

    override fun useMap(): Boolean {
        return true
    }

    fun defaultMarker(latLng: LatLng): MarkerOptions {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.draggable(true)
        markerOptions.icon(ResourceUtil.getBitmapDescriptorFactory(R.mipmap.ic_map))
        markerOptions.isFlat = true
        Constant.locAddress?.let {
            markerOptions.title(it)
        }
        return markerOptions
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}