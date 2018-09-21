package com.tima.common.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.location.AMapLocationClient
import com.tima.common.BuildConfig
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.OnlySetOnce
import org.litepal.LitePal
import org.litepal.LitePalApplication

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
class App :MultiDexApplication(){

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

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        LitePal.initialize(this)
    }
}