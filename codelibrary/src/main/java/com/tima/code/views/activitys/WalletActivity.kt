package com.tima.code.views.activitys

import android.os.Bundle
import android.view.View
import com.tima.code.R
import com.tima.code.views.dialog.PaymentResultDialog
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_wallet.*

/**
 * 钱包-界面
 * Created by Administrator on 2018/8/30/030.
 */
class WalletActivity : BaseActivity(), View.OnClickListener{


    override fun getLayoutId(): Int {
        return R.layout.code_activity_wallet
    }

    override fun inits(savedInstanceState: Bundle?) {
        tv_withdraw_cash.setOnClickListener(this)
        tv_account_recharge.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_account_recharge->{
                PaymentResultDialog(this).showDialog()
            }
            R.id.tv_withdraw_cash->{

            }
        }
    }
}
