package com.tima.code.views.fragments.news

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.chat.helper.ChatUtils
import com.tima.chat.ui.activity.ChatActivity
import com.tima.code.R
import com.tima.code.views.adapter.InteractionAdapter
import com.tima.common.base.*
import kotlinx.android.synthetic.main.code_fragment_interaction.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.litepal.LitePal
import java.util.*

/**
 * 互动
 * Created by Administrator on 2018/8/27/027.
 */
@Route(path = RoutePaths.interactionfragment)
class InteractionFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener{

    var interactionAdapter : InteractionAdapter? = null
    var presonInfos = ArrayList<PresonInfo>()

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_interaction
    }

    override fun initView() {
        swipe_interaction.setOnRefreshListener(this)
        refreshData()
        onRefreshAdpater()
    }

    override fun lazyLoad() {
    }

    override fun onRefresh() {
        swipe_interaction.isRefreshing = false
    }

    fun onRefreshAdpater(){
        sortData()
        if (interactionAdapter == null){
            interactionAdapter = InteractionAdapter(R.layout.code_recycler_interaction_item, presonInfos,activity)
            recycler_interaction.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            recycler_interaction.adapter = interactionAdapter
            interactionAdapter?.setOnItemClickListener({
                adapter, view, position ->onItemChildClick(presonInfos[position])
            })

        }else{
            interactionAdapter!!.notifyDataSetChanged()
        }
    }

    fun sortData(){
        Collections.sort(presonInfos,kotlin.Comparator { o1, o2 ->
            if (o1.newsCount > o2.newsCount) -1 else if (o1.newsCount == o2.newsCount) 0 else 1
        })
    }

     fun onItemChildClick(presonInfo: PresonInfo) {
         var intent = Intent(activity, ChatActivity::class.java)
         intent.putExtra("friendId",presonInfo.userName)
         activity.startActivity(intent)
    }

    fun showLoading() {
        loadingManage.show()
    }

    fun hideLoading() {
        loadingManage.dismiss()
    }


    fun refreshData(){
       /* presonInfos.add(PresonInfo("老王","13764217962",22,"22"))
        presonInfos.add(PresonInfo("李四","18390317132",0,"22"))
        presonInfos.add(PresonInfo("王五","18390317132",2,"22"))
        presonInfos.add(PresonInfo("六六","18390317132",4,"22"))
        presonInfos.add(PresonInfo("老七","18390317132",13,"22"))*/
        presonInfos.clear()
        var presons = LitePal.findAll(PresonInfo::class.java)
        if (presons == null || presons.size == 0){
            var presonInfo =PresonInfo("18390317132","18390317132",0,"")
            presonInfo.save()
            presonInfos.add(presonInfo)
        }else{
            presonInfos.addAll(presons)
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun reciviceBus(event: MsgEventData) {
        when(event.functionName){
            ChatUtils.REFRESH_CHAT ->{
                refreshData()
                onRefreshAdpater()
            }
        }
    }

}