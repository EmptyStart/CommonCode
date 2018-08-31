package com.tima.code.views.activitys

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tima.code.R
import com.tima.code.views.adapter.mine.TradeFlowAdapter
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_trade_flow.*

/**
 *  交易流水-界面
 *  Created by Administrator on 2018/8/30/030.
 */
class TradeFlowActivity : BaseActivity(),TradeFlowAdapter.OnTradeFlowListener,SwipeRefreshLayout.OnRefreshListener{

    var tradeflowAdapter : TradeFlowAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.code_activity_trade_flow
    }

    override fun inits(savedInstanceState: Bundle?) {
        swipe_trade_flow.setOnRefreshListener(this)
        abv_type2.setOnRightImageListener(View.OnClickListener {
            finish()
        })
        onRefreshTradeFlowAdapter()
    }

    override fun onRefresh() {
        swipe_trade_flow.isRefreshing = false
    }

    /**
     * 刷新-交易流水
     */
    fun onRefreshTradeFlowAdapter(){
        if (tradeflowAdapter == null){
            tradeflowAdapter = TradeFlowAdapter(this,this)
            recycler_trade_flow.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            recycler_trade_flow.adapter = tradeflowAdapter
        }else{
            tradeflowAdapter?.notifyDataSetChanged()
        }
    }

    override fun onClickTradeFlowItem() {
    }
}