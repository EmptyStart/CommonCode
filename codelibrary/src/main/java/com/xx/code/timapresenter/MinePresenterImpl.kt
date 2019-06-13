package com.xx.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.code.timaconstracts.IMinePresent
import com.xx.code.timaconstracts.IMineView
import com.xx.code.timaviewmodels.MineViewModelImpl
import com.xx.code.views.activitys.WalletActivity
import com.xx.common.alipay.AlipayUtils
import com.xx.common.alipay.PayBackListener
import com.xx.common.alipay.PayOrderListener
import com.xx.common.base.Constant
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener
import com.xx.common.base.RoutePaths
import com.xx.common.https.ExceptionDeal
import com.xx.common.utils.ActivityManage
import com.xx.common.utils.SpHelper

/**
 * Created by Administrator on 2018/8/30/030.
 */
class MinePresenterImpl : IMinePresent {
    var view: IMineView? = null
    var mineActivity: Activity? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { MineViewModelImpl() }

    constructor(view: IBaseViews) {
        this.view = view as IMineView
        mineActivity = view?.getMineActivity()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_help -> {

            }
            R.id.onlineService -> {
                view?.callPhone()
            }
            R.id.ll_shell -> {
//                view?.showError("认证")
            }
            R.id.ll_wallet -> {
                var intent = Intent(mineActivity, WalletActivity::class.java)
                mineActivity!!.startActivity(intent)
            }
            R.id.ll_resume -> {
//                getorder("0.01")
            }
            R.id.tv_company_name,R.id.iv_logo -> {
                view?.gotoChangeInfo()
            }
            R.id.tvLogout -> {
                logoutDeal()

            }
        }
    }

    private fun getorder(money: String){
        view?.showLoading()
        AlipayUtils.addOnAlipayListener(object : PayOrderListener{
            override fun productOrder(orderInfo: String) {
                view?.hideLoading()


            }

            override fun money(): String? =money
        })
    }

    //支付宝充值
    private fun payAlipay(orderInfo : String){
        AlipayUtils.pay(orderInfo,object : PayBackListener{
            override fun payBack(result: String?) {

            }
        })
    }

    private fun logoutDeal() {
        val mobile = Constant.mobile
        if (mobile.isNotEmpty()) {
            view?.showLoading()
            mViewMode.logout(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return mapOf(Pair("mobile", mobile))
                }

                override fun successData(success: String) {
                    view?.hideLoading()
                    view?.showError("退出登录成功")
                    SpHelper.clearPreference()
                    ARouter.getInstance().build(RoutePaths.login).navigation()
                    ActivityManage.instance.exitExcept("LoginActivity")
                }

                override fun errorData(error: String) {
                    view?.hideLoading()
//                    view?.showError("退出登录失败")
                    ExceptionDeal.handleException(error)
                }

            })

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }

}