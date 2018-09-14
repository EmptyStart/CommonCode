package com.tima.code.timaconstracts

import android.app.Activity
import android.widget.TextView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.HttpRequest
import com.tima.common.utils.IAMapLocationSuccessListener

/**
 * 钱包-界面
 * Created by jjy on 2018/9/7.
 */
interface IWalletViewModel : IBaseViewModel {
    fun addOnBodyDataListener(listener: IDataListener,url : String,requet : HttpRequest)
}

interface IWalletPresent : IBasePresenter

interface IWalletView : IBaseViews {
    fun getWalletActvity() : Activity

    fun getTvPaymentNumView() : TextView

    fun getTvAccountBalanceView() : TextView
}