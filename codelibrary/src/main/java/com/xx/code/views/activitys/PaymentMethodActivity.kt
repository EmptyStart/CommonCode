package com.xx.code.views.activitys

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.xx.code.R
import com.xx.code.constracts.IPaymentMethodView
import com.xx.code.presenter.PaymentMethodPresenterImpl
import com.xx.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_payment_method.*
import org.jetbrains.anko.toast

/**
 *  收款方式
 *  Created by jjy on 2018/8/31/031.
 */
class PaymentMethodActivity : BaseActivity(), IPaymentMethodView,View.OnClickListener{

    var paymentType = -1                                               // 1、支付宝   2、微信
    var paymentPresent : PaymentMethodPresenterImpl? = null

    override fun getLayoutId(): Int {
        return R.layout.code_activity_payment_method
    }

    override fun inits(savedInstanceState: Bundle?) {
        paymentType = intent.getIntExtra("paymentType",-1)
        if (paymentType == -1){
            toast("收款方式Error")
            finish()
        }else if (paymentType == 2){
            tv_payment_name.text = "微信"
            iv_payment_logo.setImageResource(R.mipmap.ic_wechat)
        }

        paymentPresent = PaymentMethodPresenterImpl(this)

        abv_type4.setOnRightTextListener(this)
        abv_type4.setOnRightImageListener(this)
        tv_payment_choose.setOnClickListener(this)
        tv_verification_code.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_actionbar_right_title->{

            }
            R.id.iv_actionbar_cancle->{
                finish()
            }
            R.id.tv_payment_choose->{

            }
            R.id.tv_verification_code->{

            }
        }
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun getPaymentMethodActivity(): Activity {
        return this
    }

    override fun showLoading() {
        loadingBar.show()
    }
}