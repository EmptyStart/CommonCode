package com.tima.code.timapresenter

import android.app.Activity
import android.content.Intent
import android.view.View
import com.tima.code.R
import com.tima.code.responsebody.Fund
import com.tima.code.responsebody.WalletCo
import com.tima.code.timaconstracts.IWalletPresent
import com.tima.code.timaconstracts.IWalletView
import com.tima.code.timaviewmodels.WalletViewModelImpl
import com.tima.code.views.activitys.PutForwardActivity
import com.tima.code.views.activitys.TradeFlowActivity
import com.tima.code.views.dialog.DialogUtils
import com.tima.common.base.IDataListener
import com.tima.common.https.CommonUrls
import com.tima.common.https.ExceptionDeal
import com.tima.common.https.HttpRequest
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.LogUtils
import org.json.JSONObject

/**
 * 钱包-界面
 * Created by jjy on 2018/9/7.
 */
class WalletPresenterImpl : IWalletPresent , DialogUtils.OnDialogListener{
    var TAG = "WalletPresenterImpl"
    var activity : Activity
    var view : IWalletView
    var viewMode: WalletViewModelImpl
    var funds = ArrayList<Fund>()
    var walletCo : WalletCo? = null

    constructor(view : IWalletView){
        this.view = view
        activity = view.getWalletActvity()
        viewMode = WalletViewModelImpl()
        fullFullData()
        fullWalletData()
    }

    fun fullFullData(){
        view?.showLoading()
        viewMode.addOnBodyDataListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"待付款列表=="+success)
                var arrays = JSONObject(success).getJSONArray("results")
                for (i in 0..arrays.length() - 1){
                    val position = GsonUtils.getGson.fromJson(arrays[i].toString(), Fund::class.java)
                    funds.add(position)
                }
                view?.hideLoading()
                view?.getTvPaymentNumView().text = funds.size.toString()
                LogUtils.i(TAG,"待付款列表  数量=="+funds.size)
            }

            override fun errorData(error: String) {
                view?.hideLoading()
                LogUtils.e(TAG,"待付款列表=="+error)
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return null
            }
        },CommonUrls.fund,HttpRequest.GET)
    }

    fun fullWalletData(){
        view?.showLoading()
        viewMode.addOnBodyDataListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"公司钱包接口=="+success)
                walletCo = GsonUtils.getGson.fromJson(success, WalletCo::class.java)
                if(walletCo != null)
                    view.getTvAccountBalanceView().text = walletCo?.available_amt.toString()
                view?.hideLoading()
            }

            override fun errorData(error: String) {
                view?.hideLoading()
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return null
            }
        },CommonUrls.wallet,HttpRequest.GET)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.tv_withdraw_cash->{
                //DialogUtils.showPaymentResultDialog(this)
                var intent = Intent(activity, PutForwardActivity::class.java)
                activity.startActivity(intent)
            }
            R.id.tv_account_recharge->{
                DialogUtils.showAccountRecharge(activity,this)
            }
            R.id.ll_stay_payment->{
                var intent = Intent(activity, TradeFlowActivity::class.java)
                intent.putExtra("tradeType",2)
                activity.startActivity(intent)
            }
            R.id.iv_actionbar_cancle->{
                activity.finish()
            }
            R.id.tv_actionbar_right_title->{
                DialogUtils.showAccountRecharge(activity,this)
            }
        }
    }

    override fun onDialogClick() {
    }
}