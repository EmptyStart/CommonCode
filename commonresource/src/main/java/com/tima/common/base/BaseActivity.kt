package com.tima.common.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zhy.autolayout.AutoLayoutActivity
import org.greenrobot.eventbus.EventBus

/**
 * @author : zhijun.li on 2018/6/27
 *   email : zhijun.li@timanetworks.com
 *
 */
abstract class BaseActivity : AutoLayoutActivity(){
    /**
     * 1080P  状态栏：70px   导航栏：159px    标签栏：144px  字体52px
     */
    private lateinit var rootView :View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView= View.inflate(this,getLayoutId(),null)
        setContentView(rootView)
        inits(savedInstanceState)
    }



    fun getRootView() : View?=rootView
    //default is not use EventBus
    fun userEventBus() :Boolean =false

    abstract fun getLayoutId() :Int
    abstract fun inits(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        //if use  and is not register
        if(userEventBus()&&!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        //if use and  is  register
        if (userEventBus()&&EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

}
