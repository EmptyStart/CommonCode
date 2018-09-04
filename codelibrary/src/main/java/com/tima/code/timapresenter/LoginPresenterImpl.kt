package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.code.ResponseBody.LoginResponseBody
import com.tima.code.ResponseBody.RegisterValidResponseBody
import com.tima.code.timaconstracts.ILoginPresent
import com.tima.code.timaconstracts.ILoginView
import com.tima.code.timaviewmodels.LoginViewModelImpl
import com.tima.common.base.Constant
import com.tima.common.base.IDataListener
import com.tima.common.base.RoutePaths
import com.tima.common.https.ApiException
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.LogUtils
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/8/30
 *   email : zhijun.li@timanetworks.com
 *
 */
class LoginPresenterImpl(mView: ILoginView) : ILoginPresent {
    var mView: ILoginView? = mView
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { LoginViewModelImpl() }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_vaild -> {
                mView?.apply {
                    val mobile = mobile()
                    if (mobile.isNullOrEmpty()) {
                        showError("请输入手机号码")
                        return
                    }
                    getValidSuccess()
                    mViewMode.addOnVerifyListener(object : IDataListener {
                        override fun successData(success: String) {
                            val validResponseBody = GsonUtils.getGson.fromJson(success, RegisterValidResponseBody::class.java)
                            val detail = validResponseBody?.detail
                            detail?.let {
                                mView?.showError(it)
                            }
                        }

                        override fun errorData(error: String) {
                            mView?.cancelValid()
                            ExceptionDeal.handleException(error)
                        }

                        override fun requestData(): Map<String, String>? {

                            return mapOf(Pair("mobile", mobile!!))
                        }
                    })
                }

            }
            R.id.tv_login -> {

                mView?.apply {
                    val mobile = mobile()
                    val valid = valid()
                    if (mobile.isNullOrEmpty()) {
                        showError("请输入手机号码")
                        return
                    }
                    if (valid.isNullOrEmpty()) {
                        showError("请输入验证码")
                        return
                    }
                    mViewMode.addOnLoginListener(object : IDataListener {
                        override fun successData(success: String) {
                            val responseBody = GsonUtils.getGson.fromJson(success, LoginResponseBody::class.java)
                            responseBody?.apply {
                                Constant.token=token
                                if ("N".equals(company)){
                                    ARouter.getInstance().build(RoutePaths.registerSelect).navigation()
                                }else{
                                    ARouter.getInstance().build(RoutePaths.mainpage).navigation()
                                }
                            }
                        }

                        override fun errorData(error: String) {
                            ExceptionDeal.handleException(error)
                        }

                        override fun requestData(): Map<String, String>? {
                            val pair = Pair("mobile", mobile!!)
                            val pair2 = Pair("code", valid!!)
                            val map = mapOf(pair, pair2)
                            return map
                        }

                    })
                }

            }
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
        mView?.cancelValid()
        mView == null
    }
}