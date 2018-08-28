package com.tima.code.views.fragments

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.views.adapter.CommunicateAdapter
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_communicate.*

/**
 * 沟通
 * Created by Administrator on 2018/8/27/027.
 */
@Route(path = RoutePaths.communicatefragment)
class CommunicateFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener,CommunicateAdapter.OnCommunicateListener{

    var communicateAdapter : CommunicateAdapter? = null


    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_communicate
    }

    override fun initView() {
        swipe_communicate.setOnRefreshListener(this)
        onRefreshAdpater()
    }

    override fun lazyLoad() {
    }

    fun onRefreshAdpater(){
        if (communicateAdapter == null){
            communicateAdapter = CommunicateAdapter(activity,this)
            recycler_communicate.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            recycler_communicate.adapter = communicateAdapter
        }else{
            communicateAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        swipe_communicate.isRefreshing = false
    }

    override fun onCommunicateClick() {
        Toast.makeText(activity,"点击通知",Toast.LENGTH_SHORT).show()
    }
}