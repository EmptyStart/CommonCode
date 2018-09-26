package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tima.code.R
import com.tima.code.responsebody.*
import com.tima.code.timaconstracts.IManageSetPresent
import com.tima.code.timaconstracts.IManageSetView
import com.tima.code.timaviewmodels.ManageSetViewModelImpl
import com.tima.code.views.adapter.manage.ManageSetAdapter
import com.tima.common.base.IDataListener
import com.tima.common.https.CommonUrls
import com.tima.common.https.ExceptionDeal
import com.tima.common.https.HttpRequest
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.LogUtils
import com.tima.common.utils.ResourceUtil
import org.jetbrains.anko.toast
import org.json.JSONObject
import kotlin.collections.ArrayList

/**
 * Created by Administrator on 2018/9/3/003.
 */
class ManageSetPresenterImpl : IManageSetPresent,BaseQuickAdapter.OnItemChildClickListener {
    var TAG = "ManageSetPresenterImpl"
    var view: IManageSetView
    var activity: Activity
    var oneApplyLists = ArrayList<ApplyList>()                                                      //One-Tab-申请职位-集合
    var twoApplyLists = ArrayList<ApplyList>()                                                      //Two-Tab-申请职位-集合
    var threeApplyLists = ArrayList<ApplyList>()                                                    //Three-Tab-申请职位-集合

    var oneSetAdapter: ManageSetAdapter? = null
    var twoSetAdapter: ManageSetAdapter? = null
    var threeSetAdapter: ManageSetAdapter? = null

    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { ManageSetViewModelImpl() }
    var position = 0
    var isSelectAll = false                                                                         //默认 不全选
    var oneRequest = true
    var twoRequest = false
    var threeRequest = false

    constructor(manageSetView: IManageSetView) {
        this.view = manageSetView
        activity = view.getManageActivity()
        fullData(getStatus(position))
    }

    private fun fullData(status : String){
        mViewMode.addManageSetListener(object : IDataListener {
            override fun successData(success: String) {
                var arrays = JSONObject(success).getJSONArray("results")
                if (position == 0) {
                    oneApplyLists.clear()
                }else if (position == 1){
                    twoApplyLists .clear()
                }else{
                    threeApplyLists .clear()
                }
                for (i in 0..arrays.length()-1){
                    var applyList = GsonUtils.getGson.fromJson(arrays[i].toString(), ApplyList::class.java)
                    if (position == 0) {
                        oneApplyLists.add(applyList)
                    }else if (position == 1){
                        twoApplyLists.add(applyList)
                    }else{
                        threeApplyLists.add(applyList)

                    }
                }
                if (position == 0) {
                    onOneRefreshAdapter()
                }else if (position == 1){
                    onTwoRefreshAdapter()
                }else{
                    onThreeRefreshAdapter()
                }
                LogUtils.i(TAG,"applyLists数量==="+arrays.length())
            }

            override fun errorData(error: String) {
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                //return mapOf(Pair("position", view.getIdInt().toString()), Pair("status",status))
                return mapOf(Pair("position", "57"), Pair("status",status))
            }
        }, CommonUrls.apply,HttpRequest.GET)
    }


    fun getStatus(position: Int) : String{
        var status = ""
        if (view.getmanageSetTypeInt() == 1 || 4 == view.getmanageSetTypeInt()){                    //报名管理
            status = if (position == 0) "00" else if (position == 1) "01" else "02"
        }else if (view.getmanageSetTypeInt() == 5) {                                                //面试管理
            status = if (position == 0) "10" else if (position == 1) "11" else "12"
        }else if (view.getmanageSetTypeInt() == 6){                                                 //入职管理
            status = if (position == 0) "11" else if (position == 1) "20" else "22"
        }else if (view.getmanageSetTypeInt() == 2){                                                 //到场管理
            status = if (position == 0) "10" else if (position == 1) "11" else "12"
        }else if (7 == view.getmanageSetTypeInt()){                                                 //全职---结算管理
            status = if (position == 0) "21" else if (position == 1) "30" else "31"
        }else if (3 == view.getmanageSetTypeInt()){                                                 //兼职---结算管理
            status = if (position == 0) "11" else if (position == 1) "35" else "36"
        }
        LogUtils.i(TAG,"状态status=="+status)
        return status
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_search -> {
                var content = this.view.getEdSearchView().text.toString()
                if (content != null){
                    sreachShowAdapter(content)
                }else{
                    activity.toast("请输入关键字")
                }
            }
            R.id.tv_click_one -> {
                fullDataPatch(getIds(position),getStatus(1))
            }
            R.id.tv_click_two -> {
                fullDataPatch(getIds(position),getStatus(2))
            }
            R.id.tv_select_one -> {
                if (position == 0) return
                this.view.getLlBottomView().visibility = View.VISIBLE
                position = 0
                toSelect(position)
                if (oneRequest)
                    onOneRefreshAdapter()
                else
                    fullData(getStatus(position))
                oneRequest = true
            }
            R.id.tv_select_two -> {
                if (position == 1) return
                this.view.getLlBottomView().visibility = View.GONE
                position = 1
                toSelect(position)
                if (twoRequest)
                    onTwoRefreshAdapter()
                else
                    fullData(getStatus(position))
                twoRequest = true
            }
            R.id.tv_select_three -> {
                if (position == 2) return
                this.view.getLlBottomView().visibility = View.GONE
                position = 2
                toSelect(position)
                if (threeRequest)
                    onThreeRefreshAdapter()
                else
                    fullData(getStatus(position))
                threeRequest = true
            }
            R.id.iv_total_check -> {
                isSelectAll = !isSelectAll
                this.view.getIvTotalCheckView().setImageResource(if (isSelectAll) R.mipmap.ic_total_check_light else R.mipmap.ic_total_check_dark)
                setAllIsSelect(isSelectAll)
            }
            R.id.iv_actionbar_cancle -> {
                activity.finish()
            }
        }
    }

    override fun toSelect(position: Int) {
        if (view.getTextSelectOneView().tag as Boolean) {
            view.getTextSelectOneView().tag = false
            view.getTextSelectOneView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view.getTextSelectOneView().setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if (view.getTextSelectTwoView().tag as Boolean) {
            view.getTextSelectTwoView().tag = false
            view.getTextSelectTwoView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view.getTextSelectTwoView().setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if (view.getTextSelectThreeView().tag as Boolean) {
            view.getTextSelectThreeView().tag = false
            view.getTextSelectThreeView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view.getTextSelectThreeView().setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        when (position) {
            0 -> {
                if (!(view.getTextSelectOneView().tag as Boolean)) {
                    view.getTextSelectOneView().tag = true
                    view.getTextSelectOneView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view.getTextSelectOneView().setTextColor(ResourceUtil.getColorId(R.color.white))
                }
            }
            1 -> {
                if (!(view.getTextSelectTwoView().tag as Boolean)) {
                    view.getTextSelectTwoView().tag = true
                    view.getTextSelectTwoView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view.getTextSelectTwoView().setTextColor(ResourceUtil.getColorId(R.color.white))
                }
            }
            2 -> {
                if (!(view.getTextSelectThreeView().tag as Boolean)) {
                    view.getTextSelectThreeView().tag = true
                    view.getTextSelectThreeView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view.getTextSelectThreeView().setTextColor(ResourceUtil.getColorId(R.color.white))
                }
            }
        }
    }


    override fun onOneRefreshAdapter() {
        if(oneSetAdapter == null) {
            oneSetAdapter = ManageSetAdapter(R.layout.code_recycler_manage_set_item, oneApplyLists, getStatus(position), view.getmanageSetTypeInt())
            view.getManageSetOneRecyclerView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            view.getManageSetOneRecyclerView().adapter = oneSetAdapter
            oneSetAdapter?.setOnItemChildClickListener(this)
        }else{
            oneSetAdapter?.notifyDataSetChanged()
        }
        view.getManageSetOneRecyclerView().visibility = View.VISIBLE
        view.getManageSetTwoRecyclerView().visibility = View.GONE
        view.getManageSetThreeRecyclerView().visibility = View.GONE
    }

    fun onTwoRefreshAdapter() {
        if (twoSetAdapter == null) {
            twoSetAdapter = ManageSetAdapter(R.layout.code_recycler_manage_set_item, twoApplyLists, getStatus(position), view.getmanageSetTypeInt())
            view.getManageSetTwoRecyclerView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            view.getManageSetTwoRecyclerView().adapter = twoSetAdapter
        }else{
            twoSetAdapter?.notifyDataSetChanged()
        }
        view.getManageSetOneRecyclerView().visibility = View.GONE
        view.getManageSetTwoRecyclerView().visibility = View.VISIBLE
        view.getManageSetThreeRecyclerView().visibility = View.GONE
    }

    fun onThreeRefreshAdapter() {
        if (threeSetAdapter == null) {
            threeSetAdapter = ManageSetAdapter(R.layout.code_recycler_manage_set_item, threeApplyLists, getStatus(position), view.getmanageSetTypeInt())
            view.getManageSetThreeRecyclerView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            view.getManageSetThreeRecyclerView().adapter = threeSetAdapter
        }else{
            threeSetAdapter?.notifyDataSetChanged()
        }
        view.getManageSetOneRecyclerView().visibility = View.GONE
        view.getManageSetTwoRecyclerView().visibility = View.GONE
        view.getManageSetThreeRecyclerView().visibility = View.VISIBLE
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.iv_manage_set_check->{
                onManageSetCheckClick(position)
            }
            R.id.tv_manage_set_two->{
                onManageSetTwoClick(position)
            }
            R.id.tv_manage_set_one->{
                onManageSetOneClick(position)
            }
        }
    }

    fun onManageSetOneClick(i : Int) {
        fullDataPatch(getApplyList(i)?.id.toString(),getStatus(1))
        twoRequest = false
    }

    fun onManageSetTwoClick(i: Int) {
        fullDataPatch(getApplyList(i)?.id.toString(),getStatus(2))
        threeRequest = false
    }

    fun getApplyList(i : Int) : ApplyList?{
        var  applyList : ApplyList? = null
        if (position == 0){
            if (oneApplyLists.size > i)
                applyList = oneApplyLists[i]
        }else if (position == 1){
            if (twoApplyLists.size > i)
                applyList = twoApplyLists[i]
        }else if (position == 2){
            if (threeApplyLists.size > i)
                applyList = threeApplyLists[i]
        }
        return applyList
    }

    fun onManageSetCheckClick(position: Int) {
        var applyList = getApplyList(position)
        if (applyList != null) {
            applyList.isSelect = !applyList.isSelect
            onOneRefreshAdapter()
        }
    }

    fun setAllIsSelect(isSelect : Boolean){
        for (i in 0..oneApplyLists.size-1){
            var apply = oneApplyLists[i]
            apply.isSelect = isSelect
        }
        oneSetAdapter?.notifyDataSetChanged()
    }

    fun fullDataPatch(ids: String,status : String){
        mViewMode.addManageSetListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"success="+success)
            }

            override fun errorData(error: String) {
                LogUtils.i(TAG,"error="+error)
                //ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return mapOf(Pair("status",status), Pair("ids",ids))
            }
        }, CommonUrls.applyBatch , HttpRequest.PATCH)

    }

    fun getIds(position: Int) : String{
        var data = ""
        for (i in 0..oneApplyLists.size - 1){
            var applyList = oneApplyLists[i]
            if (applyList.isSelect)
                data = data + applyList.id +","
        }
        if (data.length > 0)
            data = data.substring(0,data.length -1)
        LogUtils.i(TAG,"Ids=="+data)
        return data
    }

    fun sreachShowAdapter(content : String){
        if (position == 0){
            if (oneSetAdapter != null) {
                oneSetAdapter?.filter?.filter(content)
            }
        }else if (position == 1){
            if (twoSetAdapter != null) {
                twoSetAdapter?.filter?.filter(content)
            }
        }else{
            if (threeSetAdapter != null) {
                threeSetAdapter?.filter?.filter(content)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}