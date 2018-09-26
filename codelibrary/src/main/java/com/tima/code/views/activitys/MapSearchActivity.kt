package com.tima.code.views.activitys

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.responsebody.LocationBean
import com.tima.common.base.BaseActivity
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.IAMapLocationSuccessListener
import com.tima.common.utils.ResourceUtil
import kotlinx.android.synthetic.main.code_activity_mapsearch.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.find

/**
 * @author : zhijun.li on 2018/9/11
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.registerMap)
class MapSearchActivity : BaseActivity(), GeocodeSearch.OnGeocodeSearchListener {
    @Autowired
    @JvmField
    var city : String?=Constant.aMapLocation?.city


    var bitmapDescriptor: BitmapDescriptor? = null

    override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
        locations.clear()
        val regeocodeAddress = p0?.regeocodeAddress
        regeocodeAddress?.apply {
            val point = streetNumber.latLonPoint
            val split1 = formatAddress.split(district)
            if (split1.size > 1) {
                locations.add(LocationBean(point.latitude, point.longitude, formatAddress, province, city, district, split1[1]))
            }
            pois?.forEach {
                val latLonPoint = it.latLonPoint
                val s = city + district + it.snippet
                locations.add(LocationBean(latLonPoint.latitude, latLonPoint.longitude, s, province, city, district, it.snippet))
            }
            showAddressPop()
        }

    }

    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {
        locations.clear()
        val geocodeAddressList = p0?.geocodeAddressList
        geocodeAddressList?.forEach {
            val formatAddress = it.formatAddress
            val split = formatAddress.split(it.district)
            if (split.size > 1) {
                locations.add(LocationBean(it.latLonPoint.latitude, it.latLonPoint.longitude, formatAddress, it.province, it.city, it.district, split[1]))
            }
            showAddressPop()
        }
    }

    protected var popSa: PopupWindow? = null
    protected var marker: Marker? = null
    private var adapter: BaseQuickAdapter<LocationBean, BaseViewHolder>? = null
    val locations = ArrayList<LocationBean>()

    var selectPosition = -1

    /**
     * 设置定位点
     */
    protected fun setMarker(latLng: LatLng): MarkerOptions {
        if(bitmapDescriptor==null) {
            bitmapDescriptor = ResourceUtil.getBitmapDescriptorFactory(R.mipmap.ic_map)
        }
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.draggable(true)
        markerOptions.icon(bitmapDescriptor)
        markerOptions.isFlat = true
        markerOptions.setInfoWindowOffset(-54, -69)
//            markerOptions.title(it)
        return markerOptions
    }

    protected val geoSearch by lazy(LazyThreadSafetyMode.NONE) {
        GeocodeSearch(this)
    }

    protected fun regeoSearch(latLng: LatLonPoint, listener: GeocodeSearch.OnGeocodeSearchListener) {
        geoSearch.setOnGeocodeSearchListener(listener)
        val query = RegeocodeQuery(latLng, 500f, GeocodeSearch.AMAP)
        geoSearch.getFromLocationAsyn(query)
    }

    protected fun qGeoSearch(name: String, listener: GeocodeSearch.OnGeocodeSearchListener) {
        geoSearch.setOnGeocodeSearchListener(listener)
        val query = GeocodeQuery(name, city)
        geoSearch.getFromLocationNameAsyn(query)
    }


    /**
     * 设置定位数据
     */
    protected fun setLocation(it: LatLng) {
//        tv_pro.text = it.province
//        tv_city.text = it.city
//        tv_county.text = it.district
//        val s = it.street + it.streetNum + it.poiName
//        et_address.setText(s)
        marker?.let {
            if (!it.isRemoved) {
                it.remove()
            }
        }
        marker = aMap.addMarker(setMarker(it))
        val newLatLng = CameraUpdateFactory.newLatLng(it)
        aMap.moveCamera(newLatLng)
        aMap.minZoomLevel = 12f
        aMap.maxZoomLevel = 18f
    }

    /**
     * 拖动定位蓝点，显示点周围的定位信息
     */
    protected fun showAddressPop() {
        if (adapter == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.code_layout_pop_recycler, null)
            val recyclerView = view.find(R.id.rvPop) as RecyclerView
            val rl_pop = view.find(R.id.rl_pop) as RelativeLayout
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = object : BaseQuickAdapter<LocationBean, BaseViewHolder>(R.layout
                    .code_recycler_pop_item, locations) {
                override fun convert(helper: BaseViewHolder?, item: LocationBean?) {
                    helper?.setText(R.id.selectAddress, item?.locAddress)
                }
            }
            rl_pop.setOnClickListener { popSa?.dismiss() }
            recyclerView.adapter = adapter
            popSa = PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                    .MATCH_PARENT, true)
            popSa?.isFocusable = true
            popSa?.isOutsideTouchable = true;
            popSa?.isTouchable = true;
            popSa?.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
            popSa?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } else {
            adapter?.setNewData(locations)
        }
        adapter?.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                selectPosition = position
                EventBus.getDefault().postSticky(upDataLocation(locations[position]))
                popSa?.dismiss()
                finish()
            }
        }
        popSa?.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0)
    }

    override fun getLayoutId(): Int {
        return R.layout.code_activity_mapsearch
    }

    /**
     * 更新定位数据
     */
    protected fun upDataLocation(it: LocationBean) {
//        tv_pro.text = it.province
//        tv_city.text = it.city
//        tv_county.text = it.district
//        et_address.setText(it.snippet)
    }

    protected fun defaultMarkerDrag() {
        aMap.setOnMarkerDragListener(object : AMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(p0: Marker?) {
                val latLng = p0?.position
                latLng?.let {
                    searchReGeoData(it)

                }
            }

            override fun onMarkerDragStart(p0: Marker?) {
            }

            override fun onMarkerDrag(p0: Marker?) {
            }
        })
    }

    fun searchReGeoData(it: LatLng) {
        regeoSearch(LatLonPoint(it.latitude, it.longitude), this)
    }

    fun searchGeoData(name: String) {
        qGeoSearch(name, this)
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
                        setLocation(LatLng(it.latitude, it.longitude))
                    }
                }
            })
            return
        }
        setLocation(LatLng(aMapLocation.latitude, aMapLocation.longitude))
    }

    override fun inits(savedInstanceState: Bundle?) {
        defaultLoaction()
        defaultMarkerDrag()
        aMap.setOnMapClickListener(object : AMap.OnMapClickListener {
            override fun onMapClick(p0: LatLng?) {
                p0?.let {
                    searchReGeoData(it)
                    setLocation(it)
                    search_view.clearFocus()
                }
            }
        })
        search_view.setIconifiedByDefault(false)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchGeoData(query)
                    search_view.clearFocus()
                }
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
        iv_actionbar_cancle.setOnClickListener { finish() }
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

    override fun onDestroy() {
        bitmapDescriptor?.recycle()
        super.onDestroy()
    }
}