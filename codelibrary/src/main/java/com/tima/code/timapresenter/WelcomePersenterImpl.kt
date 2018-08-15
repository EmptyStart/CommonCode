package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import com.tima.code.timaconstracts.IWelcomePresent
import com.tima.code.timaconstracts.IWelcomeView
import com.tima.code.timaconstracts.IWelcomeViewModel

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
class WelcomePersenterImpl : IWelcomePresent {


    var view : IWelcomeView? = null
    var viewMode : IWelcomeViewModel?=null


//    constructor(view :IBaseViews?){
//        this.view= view as IWelcomeView?
//
//    }


    override fun onClick(view: View?) {
        Log.i("tag","onclick event")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        Log.i("tag","onCreate")
//        Super calls to java default methods are prohibited in JVM target 1.6 Recompile with '-jvm-target 1.8'
    }
}