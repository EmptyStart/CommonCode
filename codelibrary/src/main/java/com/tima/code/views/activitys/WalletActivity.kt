package com.tima.code.views.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tima.code.R
import com.tima.code.views.dialog.DialogUtils
import com.tima.code.views.dialog.PaymentResultDialog
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_wallet.*

/**
 * 钱包-界面
 * Created by Administrator on 2018/8/30/030.
 */
class WalletActivity : BaseActivity(), View.OnClickListener, DialogUtils.OnDialogListener{

    override fun getLayoutId(): Int {
        return R.layout.code_activity_wallet
    }

    override fun inits(savedInstanceState: Bundle?) {
        abv_type4.setOnRightTextListener(this)
        abv_type4.setOnRightImageListener(this)
        ll_stay_payment.setOnClickListener(this)
        tv_withdraw_cash.setOnClickListener(this)
        tv_account_recharge.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_withdraw_cash->{
                //DialogUtils.showPaymentResultDialog(this)
                var intent = Intent(this,PutForwardActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_account_recharge->{
                DialogUtils.showAccountRecharge(this,this)
            }
            R.id.ll_stay_payment->{
                var intent = Intent(this,TradeFlowActivity::class.java)
                intent.putExtra("tradeType",2)
                startActivity(intent)
            }
            R.id.iv_actionbar_cancle->{
                finish()
            }
            R.id.tv_actionbar_right_title->{
                DialogUtils.showAccountRecharge(this,this)
            }
        }
    }

    override fun onDialogClick() {
    }
}
