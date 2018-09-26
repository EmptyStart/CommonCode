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
import com.tima.code.responsebody.Fund
import com.tima.code.timaconstracts.ITradeFlowPresent
import com.tima.code.timaconstracts.ITradeFlowView
import com.tima.code.timaviewmodels.TradeFlowViewModelImpl
import com.tima.code.views.activitys.PersonnelRosterActivity
import com.tima.code.views.adapter.mine.PendingPeymentAdapter
import com.tima.code.views.adapter.mine.TradeFlowAdapter
import com.tima.common.base.IDataListener
import com.tima.common.https.CommonUrls
import com.tima.common.https.ExceptionDeal
import com.tima.common.https.HttpRequest
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.LogUtils
import org.json.JSONObject

/**
 * Created by Administrator on 2018/9/1/001.
 */
class TradeFlowPresenterImpl : ITradeFlowPresent,BaseQuickAdapter.OnItemChildClickListener{
    var TAG = "TradeFlowPresenterImpl"

    var tradeflowAdapter: TradeFlowAdapter? = null
    var activity: Activity
    var view: ITradeFlowView
    var funds: ArrayList<Fund>? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { TradeFlowViewModelImpl() }

    constructor(view: ITradeFlowView) {
        this.view = view
        activity = view?.getTradeActivity()
    }

    override fun init(tradeType: Int) {
        if (tradeType == 1) {
            onRefreshTradeFlowAdapter()
        } else if (tradeType == 2) {
            funds = ArrayList()
            fullData(CommonUrls.fund)
        }
    }

    fun fullData(url: String) {
        view?.showLoading()
        mViewMode.addTradeFlowListener(object : IDataListener {
            override fun successData(success: String) {
                var arrays = JSONObject(success).getJSONArray("results")
                if (url.equals(CommonUrls.fund)) {
                    for (i in 0..arrays.length() - 1) {
                        val position = GsonUtils.getGson.fromJson(arrays[i].toString(), Fund::class.java)
                        funds?.add(position)
                    }
                    onRefreshPendingPeymentAdapter()
                    LogUtils.i(TAG, "待付款列表  数量==" + funds?.size)
                }
                view?.hideLoading()
            }

            override fun errorData(error: String) {
                view?.hideLoading()
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return null
            }
        }, url,HttpRequest.GET)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_actionbar_cancle -> {
                this.view?.onFinish()
            }
        }
    }

    fun onPayment(position : Int,amount : Double){
        view?.showLoading()
        mViewMode.addTradeFlowListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"待付款支付=="+success)
                view?.hideLoading()
            }

            override fun errorData(error: String) {
                view?.hideLoading()
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return mapOf(Pair("position", position.toString()), Pair("amount", amount.toString()))
            }
        },CommonUrls.fundPay, HttpRequest.POST)
    }

    /**
     * 刷新-交易流水
     */
    fun onRefreshTradeFlowAdapter() {
        if (tradeflowAdapter == null) {
            var datas = listOf<String>("11", "", "", "", "", "", "", "")
            tradeflowAdapter = TradeFlowAdapter(R.layout.code_recycler_trade_flow_item, datas)
            view?.getRecyclerTradeFlowView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            view?.getRecyclerTradeFlowView().adapter = tradeflowAdapter
            tradeflowAdapter?.setOnItemClickListener({ adapter, view, position ->
                onClickTradeFlowItem(position)
            })
        } else {
            tradeflowAdapter?.notifyDataSetChanged()
        }
    }

    /**
     * 刷新-待付款
     */
    var paymentAdpater: PendingPeymentAdapter? = null
    fun onRefreshPendingPeymentAdapter() {
        if (paymentAdpater == null) {
            paymentAdpater = PendingPeymentAdapter(R.layout.code_recycler_pending_payment_item, funds!!)
            view?.getRecyclerTradeFlowView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            view?.getRecyclerTradeFlowView().adapter = paymentAdpater
            paymentAdpater?.setOnItemClickListener({ adapter, view, position ->
                onPendingPeymentClick(funds!![position].position)
            })
            paymentAdpater?.setOnItemChildClickListener(this)
        } else {
            paymentAdpater?.notifyDataSetChanged()
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view?.id){
            R.id.tv_payment->{
                onPayment(funds!![position].position,funds!![position].amount)
            }
        }
    }

    fun onClickTradeFlowItem(position: Int) {
        var intent = Intent(activity, PersonnelRosterActivity::class.java)
        activity.startActivity(intent)
    }

    fun onPendingPeymentClick(position : Int) {
        var intent = Intent(activity, PersonnelRosterActivity::class.java)
        intent.putExtra("position",position)
        activity.startActivity(intent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}