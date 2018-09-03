package com.tima.code.views.activitys

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tima.code.R
import com.tima.code.timaconstracts.ITradeFlowView
import com.tima.code.timapresenter.TradeFlowPresenterImpl
import com.tima.code.views.adapter.mine.TradeFlowAdapter
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_trade_flow.*
import org.jetbrains.anko.toast

/**
 *  交易流水||待付款-界面
 *  Created by Administrator on 2018/8/30/030.
 */
class TradeFlowActivity : BaseActivity(),SwipeRefreshLayout.OnRefreshListener,ITradeFlowView,View.OnClickListener{
    var tradeType : Int = -1                                                                             // 1、交易流水   2、待付款
    var tradeFlow : TradeFlowPresenterImpl?= null

    override fun getLayoutId(): Int {
        return R.layout.code_activity_trade_flow
    }

    override fun inits(savedInstanceState: Bundle?) {
        swipe_trade_flow.setOnRefreshListener(this)
        abv_type2.setOnRightImageListener(this)

        tradeType = intent.getIntExtra("tradeType",-1)
        if (tradeType == -1){
            toast("界面异常!")
            finish()
        }
        tradeFlow = TradeFlowPresenterImpl(this)
        tradeFlow?.init(tradeType)
    }

    override fun onRefresh() {
        swipe_trade_flow.isRefreshing = false
    }

    override fun onClick(v: View?) {
        tradeFlow?.onClick(v)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }

    override fun onFinish() {
        finish()
    }

    override fun getRecyclerTradeFlowView(): RecyclerView {
        return recycler_trade_flow
    }

    override fun getTradeActivity(): Activity {
        return this
    }
}