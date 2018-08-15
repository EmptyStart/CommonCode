package com.tima.common.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.OnlySetOnce

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
class App :Application(){

    companion object {
        var app :Application by OnlySetOnce.OnlySetOnceExt()
    }
    override fun onCreate() {
        super.onCreate()
        app=this;



        registerActivityLifecycleCallbacks(object :ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
                ActivityManage.instance.addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                ActivityManage.instance.removeActivity(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }

        })
    }
}