package com.xx.code.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.code.responsebody.*
import com.xx.code.constracts.ILoginPresent
import com.xx.code.constracts.ILoginView
import com.xx.code.viewmodels.LoginViewModelImpl
import com.xx.common.base.Constant
import com.xx.common.base.IDataListener
import com.xx.common.base.RoutePaths
import com.xx.common.https.ExceptionDeal
import com.xx.common.utils.ActivityManage
import com.xx.common.utils.GsonUtils
import com.xx.common.utils.SpHelper
import com.xx.common.utils.genericClass
import org.litepal.LitePal

/**
 * @author : zhijun.li on 2018/8/30
 *   email :
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
                    showLoading()
                    mViewMode.addOnVerifyListener(object : IDataListener {
                        override fun successData(success: String) {
                            hideLoading()
                            val validResponseBody = GsonUtils.getGson.fromJson(success, genericClass<RegisterValidResponseBody>())
                            val detail = validResponseBody?.detail
                            detail?.let {
                                mView?.showError(it)
                            }
                        }

                        override fun errorData(error: String) {
                            hideLoading()
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
                    showLoading()
                    mViewMode.addOnLoginListener(object : IDataListener {
                        override fun successData(success: String) {
                            hideLoading()
                            val responseBody = GsonUtils.getGson.fromJson(success, LoginResponseBody::class.java)
                            val responseBodyNoId = GsonUtils.getGson.fromJson(success,
                                    LoginResponseBodyNoId::class.java)
                            responseBody?.apply {
                                LitePal.deleteAll(LoginResponseBody::class.java)
                                LitePal.deleteAll(Hr::class.java)
                                LitePal.deleteAll(Company::class.java)
                                save()
                                hr.save()
                                hr.company.save()

                                mView?.bindAccount(hr.mobile)
                                SpHelper(Constant.LOGENINFO, success)
                                Constant.token = token
                                Constant.mobile = hr.mobile
                                Constant.chatPassword = huanxin_password
                                Constant.companyId = responseBodyNoId.hr.company.id.toString()
                                val hrId = responseBodyNoId.hr.id.toString();
                                Constant.hrId = hrId
                                Constant.partCommit = parttime_commition
                                if ("0"==hr.company.type){
                                    //个人
                                    Constant.position=1
                                }else if ("1"==hr.company.type){
                                    //公司
                                    Constant.position=2
                                }
                                Constant.companyState=company
                                if ("N".equals(company)) {
                                    ARouter.getInstance().build(RoutePaths.registerSelect).navigation()
                                } else {
                                    val verify = hr.company.verify
                                    Constant.verify=verify
                                    if("1"==verify){
                                        ARouter.getInstance().build(RoutePaths.mainpage).navigation()
                                    }
                                    if ("2"==verify){
                                        if (Constant.position==1){
                                            ARouter.getInstance().build(RoutePaths.registerPrivate).navigation()
                                        }else if (Constant.position==2){
                                            ARouter.getInstance().build(RoutePaths.registerCompany).navigation()
                                        }
                                    }
                                }
                                ActivityManage.instance.exitCurrentActivity()
                            }
                        }

                        override fun errorData(error: String) {
                            hideLoading()
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