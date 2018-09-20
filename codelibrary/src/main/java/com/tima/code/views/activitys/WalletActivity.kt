package com.tima.code.views.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.tima.code.R
import com.tima.code.ResponseBody.Position
import com.tima.code.timaconstracts.IWalletView
import com.tima.code.timapresenter.WalletPresenterImpl
import com.tima.code.views.dialog.DialogUtils
import com.tima.code.views.dialog.PaymentResultDialog
import com.tima.common.base.BaseActivity
import com.tima.common.base.IDataListener
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.CommonUrls
import com.tima.common.https.ExceptionDeal
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.IAMapLocationSuccessListener
import com.tima.common.utils.LoadingBarManage
import com.tima.common.utils.LogUtils
import kotlinx.android.synthetic.main.code_activity_wallet.*
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * 钱包-界面
 * Created by Administrator on 2018/8/30/030.
 */
class WalletActivity : BaseActivity(), View.OnClickListener,IWalletView{

    var walletPresent : WalletPresenterImpl? = null
    override fun getLayoutId(): Int {
        return R.layout.code_activity_wallet
    }

    override fun inits(savedInstanceState: Bundle?) {
        abv_type4.setOnRightTextListener(this)
        abv_type4.setOnRightImageListener(this)
        ll_stay_payment.setOnClickListener(this)
        tv_withdraw_cash.setOnClickListener(this)
        tv_account_recharge.setOnClickListener(this)

        walletPresent = WalletPresenterImpl(this)
        lifecycle.addObserver(walletPresent!!)
    }

    override fun onClick(v: View?) {
        walletPresent?.onClick(v)
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun showLoading() {
        loadingBar.show()
    }

    override fun getWalletActvity(): Activity {
        return this
    }

    override fun getTvPaymentNumView(): TextView {
        return tv_payment_num
    }

    override fun getTvAccountBalanceView(): TextView {
        return tv_account_balance
    }
}
