package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tima.code.R
import com.tima.code.R.id.recycler_trade_flow
import com.tima.code.timaconstracts.ITradeFlowPresent
import com.tima.code.timaconstracts.ITradeFlowView
import com.tima.code.timaviewmodels.TradeFlowViewModelImpl
import com.tima.code.views.activitys.PersonnelRosterActivity
import com.tima.code.views.activitys.TradeFlowActivity
import com.tima.code.views.adapter.mine.PendingPeymentAdapter
import com.tima.code.views.adapter.mine.TradeFlowAdapter
import com.tima.code.views.adapter.part.PartPublishedAdapter
import kotlinx.android.synthetic.main.code_activity_trade_flow.*

/**
 * Created by Administrator on 2018/9/1/001.
 */
class TradeFlowPresenterImpl : ITradeFlowPresent ,PendingPeymentAdapter.OnPendingPeymentListener{


    var tradeflowAdapter : TradeFlowAdapter? = null
    var activity : Activity
    var view : ITradeFlowView
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { TradeFlowViewModelImpl() }
    constructor(view : ITradeFlowView){
        this.view = view
        activity = view?.getTradeActivity()
    }

    override fun init(tradeType: Int) {
        if (tradeType == 1){
            onRefreshTradeFlowAdapter()
        }else if (tradeType == 2){
            onRefreshPendingPeymentAdapter()
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.iv_actionbar_cancle->{
                this.view?.onFinish()
            }
        }
    }

    /**
     * 刷新-交易流水
     */
    fun onRefreshTradeFlowAdapter(){
        if (tradeflowAdapter == null){
            var datas = listOf<String>("11","","","","","","","")
            tradeflowAdapter = TradeFlowAdapter(R.layout.code_recycler_trade_flow_item, datas)
            //tradeflowAdapter = TradeFlowAdapter(activity,this)
            view?.getRecyclerTradeFlowView().layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            view?.getRecyclerTradeFlowView().adapter = tradeflowAdapter
            tradeflowAdapter?.setOnItemClickListener({
                adapter, view, position ->onClickTradeFlowItem(position)
            })
        }else{
            tradeflowAdapter?.notifyDataSetChanged()
        }
    }

    /**
     * 刷新-待付款
     */
    var paymentAdpater : PendingPeymentAdapter? =null
    fun onRefreshPendingPeymentAdapter(){
        if (paymentAdpater == null){
            var datas = listOf<String>("11","","","","","","","")
            paymentAdpater = PendingPeymentAdapter(R.layout.code_recycler_pending_payment_item, datas)
            //paymentAdpater = PendingPeymentAdapter(activity,this)
            view?.getRecyclerTradeFlowView().layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            view?.getRecyclerTradeFlowView().adapter = paymentAdpater
            paymentAdpater?.setOnItemClickListener({
                adapter, view, position ->onPendingPeymentClick()
            })
        }else{
            paymentAdpater?.notifyDataSetChanged()
        }
    }

    fun onClickTradeFlowItem(position : Int) {
        var intent = Intent(activity,PersonnelRosterActivity::class.java)
        activity.startActivity(intent)
    }

    override fun onPendingPeymentClick() {
        var intent = Intent(activity,PersonnelRosterActivity::class.java)
        activity.startActivity(intent)
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}