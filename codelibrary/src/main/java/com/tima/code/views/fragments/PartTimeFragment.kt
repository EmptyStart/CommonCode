package com.tima.code.views.fragments

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.timaconstracts.IPartTimeView
import com.tima.code.views.adapter.PublishedAdapter
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_part_time.*

/**
 * 管理-兼职
 * Created by Administrator on 2018/8/28/028.
 */
@Route(path = RoutePaths.manageptfragment)
class PartTimeFragment : BaseFragment(),PublishedAdapter.OnPublishedListener,SwipeRefreshLayout.OnRefreshListener,IPartTimeView{

    var publishedAdapter : PublishedAdapter? = null
    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_part_time
    }

    override fun initView() {
        refreshPublishedAdapter()
        swipe_part_time.setOnRefreshListener(this)
    }

    override fun lazyLoad() {
    }

    /**
     * 刷新已发布配器
     */
    fun refreshPublishedAdapter() {
        if (publishedAdapter == null) {
            publishedAdapter = PublishedAdapter(activity, this)
            recycler_part_time.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            recycler_part_time.adapter = publishedAdapter
        } else {
            publishedAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        swipe_part_time.isRefreshing = false
    }

    override fun onPublishedClick() {
        Toast.makeText(activity,"点击已发布", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }
}