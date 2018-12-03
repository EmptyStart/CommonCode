package com.tima.common.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager
import com.tima.common.BuildConfig
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.LogUtils
import com.tima.common.utils.OnlySetOnce
import com.tima.common.utils.SettingUtils
import org.litepal.LitePal

/**
 * @author : zhijun.li on 2018/6/28
 *   email :
 *
 */
class App :MultiDexApplication(){

    companion object {
        var app :Application by OnlySetOnce.OnlySetOnceExt()
    }
    override fun onCreate() {
        super.onCreate()
        app = this;
        if (SettingUtils.isMainProcess(this)) {
            registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
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
                XGPushConfig.enableDebug(this,true)
            }
            ARouter.init(this); // 尽可能早，推荐在Application中初始化
            LitePal.initialize(this)
            if (Constant.token.isNotEmpty()) {
                XGPushManager.bindAccount(this, Constant.mobile, object : XGIOperateCallback {
                    override fun onSuccess(p0: Any?, p1: Int) {
                        LogUtils.i("TPUSH", "注册成功，token为" + p0.toString())
                    }

                    override fun onFail(p0: Any?, p1: Int, p2: String?) {
                        LogUtils.i("TPUSH", "注册失败，错误码：" + p1 + ",错误信息：" + p2)
                    }
                })
            }
        }
    }
}