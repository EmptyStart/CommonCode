package com.tima.common.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zhy.autolayout.AutoLayoutActivity

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

    abstract fun getLayoutId() :Int
    abstract fun inits(savedInstanceState: Bundle?)

}
