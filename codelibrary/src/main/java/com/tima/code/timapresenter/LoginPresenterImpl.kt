package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.tima.code.R
import com.tima.code.ResponseBody.LoginResponseBody
import com.tima.code.timaconstracts.ILoginPresent
import com.tima.code.timaconstracts.ILoginView
import com.tima.code.timaviewmodels.LoginViewModelImpl
import com.tima.common.base.Constant
import com.tima.common.base.IDataListener
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
                mViewMode.addOnVerifyListener(object : IDataListener {
                    override fun successData(success: String) {

                    }

                    override fun errorData(error: String) {
                        ExceptionDeal.handleException(error)
                    }

                    override fun requestData(): Map<String, String>? {

                        return mapOf(Pair("mobile", "17615009211"))
                    }
                })
            }
            R.id.tv_login -> {

                mView?.apply {
                    val mobile = mobile()
                    val valid = valid()
//                    if (mobile.isNullOrEmpty()){
//                        it.showError("请输入手机号码")
//                        return
//                    }
                    if (valid.isNullOrEmpty()) {
                        showError("请输入验证码")
                        return
                    }
                    mViewMode.addOnLoginListener(object : IDataListener {
                        override fun successData(success: String) {
                            val responseBody = GsonUtils.getGson.fromJson(success, LoginResponseBody::class.java)
                            val token = responseBody?.token
                            token?.let {
                                Constant.token = it
                            }
                        }

                        override fun errorData(error: String) {
                            ExceptionDeal.handleException(error)
                        }

                        override fun requestData(): Map<String, String>? {

                            val pair = Pair("mobile", "17615009211")
                            val pair2 = Pair("code", valid.orEmpty())
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
    }
}