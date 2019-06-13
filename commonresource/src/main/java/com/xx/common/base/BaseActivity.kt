package com.xx.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.xx.common.utils.IAMapLocationSuccessListener
import com.xx.common.utils.LoadingBarManage
import com.xx.common.utils.MapGaoDe
import com.zhy.autolayout.AutoLayoutActivity
import org.greenrobot.eventbus.EventBus

/**
 * @author : zhijun.li on 2018/6/27
 *   email :
 *
 */
abstract class BaseActivity : AutoLayoutActivity() {
    /**
     * 1080P  状态栏：70px   导航栏：159px    标签栏：144px  字体52px
     */
    private lateinit var rootView: View
    protected lateinit var mapView: MapView
    protected lateinit var aMap: AMap
    val loadingBar by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LoadingBarManage(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        rootView = View.inflate(this, getLayoutId(), null)

        setContentView(rootView)
        ARouter.getInstance().inject(this)
        if (useMap()) {
            setMap()?.let {
                this.mapView = it
                aMap = it.map
            }
            mapView?.let {
                it.onCreate(savedInstanceState)
            }
        }
        inits(savedInstanceState)
    }

    open fun location(listener: IAMapLocationSuccessListener) {
        MapGaoDe.getLocation(this, listener)
    }

    open fun getRootView(): View? = rootView
    //default is not use EventBus
    open fun useEventBus(): Boolean = false

    open fun useMap(): Boolean = false
    open fun setMap(): MapView? = null
    abstract fun getLayoutId(): Int
    abstract fun inits(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        //if use  and is not register
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (useMap()) {
            mapView?.let {
                it.onSaveInstanceState(outState)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (useMap()) {
            mapView?.let {
                it.onResume()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (useMap()) {
            mapView?.let {
                it.onPause()
            }
        }
    }

    override fun onDestroy() {
        //if use and  is  register
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
        if (useMap()) {
            mapView?.let {
                it.onDestroy()
            }
        }
        loadingBar?.let {
            it.destroy()
        }
    }

}
