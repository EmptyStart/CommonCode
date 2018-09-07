package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.HttpRequest

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