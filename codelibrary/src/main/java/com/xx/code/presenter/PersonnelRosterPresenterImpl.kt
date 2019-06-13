package com.xx.code.presenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xx.code.R
import com.xx.code.responsebody.FundDetail
import com.xx.code.constracts.IPersonnelRosterPresent
import com.xx.code.constracts.IPersonnelRosterView
import com.xx.code.viewmodels.PersonnelRosterViewModelImpl
import com.xx.code.views.adapter.mine.PersonnelRosterAdapter
import com.xx.common.base.IDataListener
import com.xx.common.https.CommonUrls
import com.xx.common.https.ExceptionDeal
import com.xx.common.https.HttpRequest
import com.xx.common.utils.GsonUtils
import com.xx.common.utils.LogUtils
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * Created by Administrator on 2018/9/1/001.
 */
class PersonnelRosterPresenterImpl : IPersonnelRosterPresent ,BaseQuickAdapter.OnItemChildClickListener{

    var TAG = "PersonnelRosterPresenterImpl"

    var personView : IPersonnelRosterView
    var activity : Activity
    var personnelAdapter : PersonnelRosterAdapter? = null
    var fundDetails = ArrayList<FundDetail>()
    var amount = 0.0
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { PersonnelRosterViewModelImpl() }
    constructor(view : IPersonnelRosterView){
        personView = view
        activity = personView.getPersonnelActivity()
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_actionbar_cancle->{
                activity.finish()
            }
            R.id.tv_sure_payment->{
                activity.toast("确认支付")
                onPayment(personView?.getPositionInt())
            }
        }
    }

    override fun init() {
        fullData(personView?.getPositionInt())
    }

    fun fullData(position : Int){
        personView?.showLoading()
        mViewMode.addPersonnelRosterListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"查询带支付明细列表=="+success)
                var arrays = JSONObject(success).getJSONArray("results")
                for (i in 0..arrays.length() - 1){
                    val fundDetail = GsonUtils.getGson.fromJson(arrays[i].toString(), FundDetail::class.java)
                    fundDetails.add(fundDetail)
                }
                personView?.hideLoading()
                onRefreshAdapter()
                onTotalMoney()
            }

            override fun errorData(error: String) {
                personView?.hideLoading()
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return mapOf(Pair("position", position.toString()))
            }
        },CommonUrls.fundDetail,HttpRequest.GET)
    }

    fun onPayment(position : Int){
        personView?.showLoading()
        mViewMode.addPersonnelRosterListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"待付款支付=="+success)
                personView?.hideLoading()
            }

            override fun errorData(error: String) {
                personView?.hideLoading()
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return mapOf(Pair("position", position.toString()), Pair("amount", amount.toString()))
            }
        },CommonUrls.fundDetail,HttpRequest.POST)
    }

    fun onTotalMoney(){
        amount = 0.0
        for (i in 0..fundDetails.size-1){
            amount += fundDetails[i].amt
        }
        personView.getTvTotalMoneyView().text = amount.toString()
    }


    override fun onRefreshAdapter() {
        if (personnelAdapter == null){
            personnelAdapter = PersonnelRosterAdapter(R.layout.code_recycler_personnel_roster_item, fundDetails)
            personView.getRecyclerPersonnelView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            personView.getRecyclerPersonnelView().adapter = personnelAdapter
            personnelAdapter?.setOnItemClickListener({ adapter, view, position ->onPersonnelRosterClick() })
            personnelAdapter?.setOnItemChildClickListener(this)
        }else{
            personnelAdapter?.notifyDataSetChanged()
        }
    }

    fun onPersonnelRosterClick() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.tv_quit->{
                activity.toast("点击离职")
            }
        }
    }
}