package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.timaconstracts.IWelcomePresent
import com.tima.code.timaconstracts.IWelcomeView
import com.tima.code.timaconstracts.IWelcomeViewModel
import com.tima.code.timaviewmodels.WelcomeViewModelImpl
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.base.RoutePaths
import com.tima.common.https.ApiException
import com.tima.common.https.ExceptionDeal
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
class WelcomePersenterImpl : IWelcomePresent {


    var view: IWelcomeView? = null
    var viewMode: IWelcomeViewModel? = null


    constructor(view: IBaseViews?) {
        this.view = view as IWelcomeView?
        viewMode = WelcomeViewModelImpl()
    }


    override fun onClick(view: View?) {
        Log.i("tag", "onclick event")
        ARouter.getInstance().build(RoutePaths.main).navigation()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        Log.i("tag", "onCreate")
//        Super calls to java default methods are prohibited in JVM target 1.6 Recompile with '-jvm-target 1.8'
//        view?.showLoading()
//        viewMode?.addOnBodyDataListener(object : IDataListener {
//            override fun successData(success: ResponseBody) {
//                //成功返回
//                view?.hideLoading()
//
//            }
//
//            override fun errorData(error: String) {
//                //错误返回
//                view?.hideLoading()
//                ExceptionDeal.handleException(ApiException(error))
//            }
//
//            override fun requestData(): Map<String, String>? {
//                //请求参数
//                return null
//            }
//
//        })
    }
}