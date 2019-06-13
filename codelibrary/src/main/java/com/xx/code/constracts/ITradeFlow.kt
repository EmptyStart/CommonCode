package com.xx.code.constracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews
import com.xx.common.base.IDataListener
import com.xx.common.https.HttpRequest

/**
 * Created by Administrator on 2018/9/1/001.
 */
interface ITradeFlowViewModel : IBaseViewModel {
    fun addTradeFlowListener(listener: IDataListener,url : String, type : HttpRequest)
}

interface ITradeFlowPresent : IBasePresenter{
    fun init(tradeType : Int)
}

interface ITradeFlowView : IBaseViews{
    fun onFinish()

    fun getRecyclerTradeFlowView() : RecyclerView

    fun getTradeActivity() : Activity

    fun getTradeTypeInt() : Int
}