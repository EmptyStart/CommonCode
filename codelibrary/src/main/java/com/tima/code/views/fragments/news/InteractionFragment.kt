package com.tima.code.views.fragments.news

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.views.adapter.InteractionAdapter
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_interaction.*

/**
 * 互动
 * Created by Administrator on 2018/8/27/027.
 */
@Route(path = RoutePaths.interactionfragment)
class InteractionFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, InteractionAdapter.OnInteractionListener {


    var interactionAdapter : InteractionAdapter? = null
    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_interaction
    }

    override fun initView() {
        swipe_interaction.setOnRefreshListener(this)
        onRefreshAdpater()
    }

    override fun lazyLoad() {
    }

    override fun onRefresh() {
        swipe_interaction.isRefreshing = false
    }

    fun onRefreshAdpater(){
        if (interactionAdapter == null){
            interactionAdapter = InteractionAdapter(activity,this)
            recycler_interaction.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            recycler_interaction.adapter = interactionAdapter
        }else{
            interactionAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onInteractionClick() {

        Toast.makeText(activity,"点击联系人", Toast.LENGTH_SHORT).show()
    }
}