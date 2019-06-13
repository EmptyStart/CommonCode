package com.xx.code.constracts

import android.app.Activity
import android.widget.TextView
import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener
import com.xx.common.https.HttpRequest

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