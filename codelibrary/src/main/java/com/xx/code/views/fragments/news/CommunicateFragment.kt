package com.xx.code.views.fragments.news

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.xx.code.views.adapter.CommunicateAdapter
import com.xx.common.base.BaseFragment
import com.xx.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_communicate.*

/**
 * 沟通
 * Created by Administrator on 2018/8/27/027.
 */
@Route(path = RoutePaths.communicatefragment)
class CommunicateFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener,CommunicateAdapter.OnCommunicateListener {

    var communicateAdapter : CommunicateAdapter? = null


    override fun attachLayoutRes(): Int {
        return com.xx.code.R.layout.code_fragment_communicate
    }

    override fun initView() {
        swipe_communicate.setOnRefreshListener(this)
        onRefreshAdpater()
    }

    override fun lazyLoad() {
    }

    fun onRefreshAdpater(){
        if (communicateAdapter == null){
            communicateAdapter = com.xx.code.views.adapter.CommunicateAdapter(activity, this)
            recycler_communicate.layoutManager = android.support.v7.widget.LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recycler_communicate.adapter = communicateAdapter
        }else{
            communicateAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        swipe_communicate.isRefreshing = false
    }

    fun showLoading() {
        loadingManage.show()
    }

    fun hideLoading() {
        loadingManage.dismiss()
    }

    override fun onCommunicateClick() {
        android.widget.Toast.makeText(activity,"点击通知", android.widget.Toast.LENGTH_SHORT).show()
    }
}