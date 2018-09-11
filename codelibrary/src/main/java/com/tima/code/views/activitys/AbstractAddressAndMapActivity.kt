package com.tima.code.views.activitys

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.ScrollView
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.*
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.ResponseBody.LocationBean
import com.tima.common.base.*
import com.tima.common.utils.CameraUtils
import com.tima.common.utils.IAMapLocationSuccessListener
import com.tima.common.utils.LogUtils
import com.tima.common.utils.ResourceUtil
import kotlinx.android.synthetic.main.code_layout_select_address.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/9/7
 *   email : zhijun.li@timanetworks.com
 *
 */
abstract class AbstractAddressAndMapActivity : BaseActivity() {
    protected var pickerPro: OptionsPickerView<String>? = null
    protected var pickerCity: OptionsPickerView<String>? = null
    protected var pickerCounty: OptionsPickerView<String>? = null

    protected var province: ProvinceBody? = null
    protected val pros = ArrayList<String>()
    protected val citys = ArrayList<String>()
    protected val countys = ArrayList<String>()
    protected var proBean: District? = null
    protected var cityBean: City? = null
    protected var marker: Marker? = null
    protected var popSa: PopupWindow? = null
    private var adapter: BaseQuickAdapter<LocationBean, BaseViewHolder>? = null
    val locations = ArrayList<LocationBean>()
    protected val geoSearch by lazy(LazyThreadSafetyMode.NONE) {
        GeocodeSearch(this)
    }
    var selectPosition = -1
    /**
     * 使用逆地图编码
     */
    protected fun geoSearch(latLng: LatLonPoint, listener: GeocodeSearch.OnGeocodeSearchListener) {
        geoSearch.setOnGeocodeSearchListener(listener)
        val query = RegeocodeQuery(latLng, 500f, GeocodeSearch.AMAP)
        geoSearch.getFromLocationAsyn(query)
    }
    protected fun qGeoSearch(name: String,city: String?, listener: GeocodeSearch.OnGeocodeSearchListener) {
        geoSearch.setOnGeocodeSearchListener(listener)
        val query = GeocodeQuery(name, city)
        geoSearch.getFromLocationNameAsyn(query)
    }
    /**
     * 选择图片
     */
    protected fun pickImage(requestCode: Int) {
        CameraUtils.matisseCameraOrAlbum(this, requestCode)
    }

    /**
     * 解决地图与ScrollView冲突
     */
    protected fun putScrollView(scrollView: ScrollView) {
        mapContainer.setScrollView(scrollView)
    }

    /**
     * 设置定位点
     */
    protected fun setMarker(latLng: LatLng, title: String?): MarkerOptions {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.draggable(true)
        markerOptions.icon(ResourceUtil.getBitmapDescriptorFactory(R.mipmap.ic_map))
        markerOptions.isFlat = true
        markerOptions.setInfoWindowOffset(-54, -69)
//        title?.let {
//            markerOptions.title(it)
//        }
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
                        setLocation(it)
                    }
                }
            })
            return
        }
        setLocation(aMapLocation)
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
            popSa?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        } else {
            adapter?.setNewData(locations)
        }
        adapter?.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                selectPosition = position
                upDataLocation(locations[position])
                popSa?.dismiss()
            }
        }
        popSa?.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0)
    }

    /**
     * 设置定位数据
     */
    protected fun setLocation(it: AMapLocation) {
        tv_pro.text = it.province
        tv_city.text = it.city
        tv_county.text = it.district
        val s = it.street + it.streetNum + it.poiName
        et_address.setText(s)
        val latLng = LatLng(it.latitude, it.longitude)
        marker?.let {
            if (!it.isRemoved) {
                it.remove()
            }
        }
        marker = aMap.addMarker(setMarker(latLng, it.address))
        val newLatLng = CameraUpdateFactory.newLatLng(latLng)
        aMap.moveCamera(newLatLng)
        aMap.minZoomLevel = 12f
        aMap.maxZoomLevel = 18f
    }

    /**
     * 更新定位数据
     */
    protected fun upDataLocation(it: LocationBean) {
        tv_pro.text = it.province
        tv_city.text = it.city
        tv_county.text = it.district
        et_address.setText(it.snippet)
    }

    /**
     * 设置点击监听
     */
    protected fun setOnMapClick(listener: AMap.OnMapClickListener) {
        aMap.setOnMapClickListener(listener)

    }

    /**
     * 设置自定义拖动监听
     */
    protected fun setOnMarkerDrag(listener: AMap.OnMarkerDragListener) {
        aMap.setOnMarkerDragListener(listener)
    }

    protected fun defaultMapClick(){
        aMap.setOnMapClickListener(object : AMap.OnMapClickListener {
            override fun onMapClick(p0: LatLng?) {
                rGeoSearch(p0)
            }
        })
    }
    /**
     * 设置默认拖动监听
     */
    protected fun defaultMarkerDrag() {
        aMap.setOnMarkerDragListener(object : AMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(p0: Marker?) {
                val latLng = p0?.position
                rGeoSearch(latLng)
            }

            override fun onMarkerDragStart(p0: Marker?) {
            }

            override fun onMarkerDrag(p0: Marker?) {
            }
        })
    }

    fun rGeoSearch(latLng: LatLng?) {
        latLng?.let {
            geoSearch(LatLonPoint(it.latitude, it.longitude), object : GeocodeSearch.OnGeocodeSearchListener {
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
                    }
                    showAddressPop()
                }

                override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {
                }
            })

        }
    }

    /**
     * 省选择
     */
    protected fun proPicker() {
        if (province == null) {
            province = Constant.province
        }
        province?.let {
            if (pros.isEmpty()) {
                for (dis in it.districts) {
                    pros.add(dis.name)
                }
            }
            pickerPro = OptionsPickerBuilder(this, object : OnOptionsSelectListener {
                override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                    tv_pro.text = pros.get(options1)
                    tv_city.setText(R.string.code_register_city)
                    tv_county.setText(R.string.code_register_city)
                    proBean = it.districts.get(options1)
                    proBean?.run {
                        citys.clear()
                        for (c in city) {
                            citys.add(c.name)
                        }
                        pickerPro?.dismiss()
                        cityPicker()
                    }
                }
            })
                    .setTitleText("选择省")
                    .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .setOptionsSelectChangeListener(object : OnOptionsSelectChangeListener {
                        override fun onOptionsSelectChanged(options1: Int, options2: Int, options3: Int) {
                            LogUtils.i("RegisterPrivateActivity2", options1.toString() + "," +
                                    "" + options2 + "," + options3)
                        }

                    })
                    .build()
            pickerPro?.setPicker(pros)
            pickerPro?.show()
            return
        }
        toast("未获取到城市数据！")

    }

    /**
     * 市选择
     */
    protected fun cityPicker() {
        if (citys.isEmpty()) {
            proPicker()
            return
        }
        pickerCity = OptionsPickerBuilder(this, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                tv_city.text = citys.get(options1)
                tv_county.setText(R.string.code_register_city)
                cityBean = proBean?.city?.get(options1)
                cityBean?.run {
                    countys.clear()
                    area.forEach {
                        countys.add(it)
                    }
                    pickerCity?.dismiss()
                    countyPicker()
                }
            }
        })
                .setTitleText("选择市")
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOptionsSelectChangeListener(object : OnOptionsSelectChangeListener {
                    override fun onOptionsSelectChanged(options1: Int, options2: Int, options3: Int) {
                        LogUtils.i("RegisterPrivateActivity2", options1.toString() + "," +
                                "" + options2 + "," + options3)
                    }

                })
                .build()
        pickerCity?.setPicker(citys)
        pickerCity?.show()
    }

    /**
     * 地区选择
     */
    protected fun countyPicker() {
        if (countys.isEmpty()) {
            cityPicker()
            return
        }
        pickerCounty = OptionsPickerBuilder(this, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                pickerCounty?.dismiss()
                tv_county.text = countys.get(options1)
            }
        })
                .setTitleText("选择区")
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOptionsSelectChangeListener(object : OnOptionsSelectChangeListener {
                    override fun onOptionsSelectChanged(options1: Int, options2: Int, options3: Int) {
                        LogUtils.i("RegisterPrivateActivity2", options1.toString() + "," +
                                "" + options2 + "," + options3)
                    }

                })
                .build()
        pickerCounty?.setPicker(countys)
        pickerCounty?.show()
    }

    override fun useEventBus(): Boolean =true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        llAddress.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
               ARouter.getInstance().build(RoutePaths.registerMap).withString("city",tv_city.text.toString())
            }
        })

        iv_pro.setOnClickListener { proPicker() }
        iv_city.setOnClickListener { cityPicker() }
        iv_county.setOnClickListener { countyPicker() }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true,priority = 1)
    fun upLocation(event : LocationBean){
        upDataLocation(event)
        EventBus.getDefault().removeStickyEvent(event)
    }

    override fun onDestroy() {
        pickerPro?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        pickerCity?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        pickerCounty?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        super.onDestroy()

    }
}