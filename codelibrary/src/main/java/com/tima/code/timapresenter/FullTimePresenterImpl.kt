package com.tima.code.timapresenter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tima.code.R
import com.tima.code.responsebody.Position
import com.tima.code.timaconstracts.IFullTimePresent
import com.tima.code.timaconstracts.IFullTimeView
import com.tima.code.timaviewmodels.FullTimeViewModelImpl
import com.tima.code.views.activitys.ManageFullTimeInfoActivity
import com.tima.code.views.adapter.full.FullPublishedAdapter
import com.tima.code.views.adapter.part.PartAlreadyDownAdapter
import com.tima.code.views.adapter.part.PartAuditAdapter
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.LogUtils
import com.tima.common.utils.ResourceUtil
import org.json.JSONObject

/**
 * 管理-全职
 * Created by Administrator on 2018/8/28/028.
 */
class FullTimePresenterImpl : IFullTimePresent, BaseQuickAdapter.OnItemChildClickListener{
    var TAG = "FullTimePresenterImpl"
    var activity : Activity? = null
    var view: IFullTimeView? = null
    var viewMode: FullTimeViewModelImpl? = null
    var positions = ArrayList<Position>()
    var showPositions = ArrayList<Position>()
    var currentPosition = 0

    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { FullTimeViewModelImpl() }

    constructor(view: IBaseViews?) {
        this.view = view as IFullTimeView?
        viewMode = FullTimeViewModelImpl()
        init()
    }

    override fun init() {
        activity = view?.getFullActivity()
        fullData()
    }


    override fun fullData(){
        view?.showLoading()
        mViewMode.addFullTimeListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"返回发布职位=="+success)
                positions.clear()
                var arrays = JSONObject(success).getJSONArray("results")
                for (i in 0..arrays.length() - 1){
                    val position = GsonUtils.getGson.fromJson(arrays[i].toString(), Position::class.java)
                    positions.add(position)
                }
                view?.hideLoading()
                toSelect(currentPosition)
                LogUtils.i(TAG,"返回发布职位  数量=="+positions.size)
            }

            override fun errorData(error: String) {
                view?.hideLoading()
                ExceptionDeal.handleException(error)
            }

            override fun requestData(): Map<String, String>? {
                return mapOf(Pair("page","1"))
            }
        })
    }


    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.tv_select_one->{
                currentPosition = 0
            }
            R.id.tv_select_two->{
                currentPosition = 1
            }
            R.id.tv_select_three->{
                currentPosition = 2
            }
        }
        toSelect(currentPosition)
    }

    override fun toSelect(position: Int) {
        if ( view?.getTextSelectOneView()?.tag as Boolean) {
            view?.getTextSelectOneView()?.tag = false
            view?.getTextSelectOneView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectOneView()?.setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if ( view?.getTextSelectTwoView()?.tag as Boolean) {
            view?.getTextSelectTwoView()?.tag = false
            view?.getTextSelectTwoView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectTwoView()?.setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if ( view?.getTextSelectThreeView()?.tag as Boolean) {
            view?.getTextSelectThreeView()?.tag = false
            view?.getTextSelectThreeView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectThreeView()?.setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        when(position){
            0->{
                if (!(view?.getTextSelectOneView()?.tag as Boolean)) {
                    view?.getTextSelectOneView()?.tag = true
                    view?.getTextSelectOneView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectOneView()?.setTextColor(ResourceUtil.getColorId(R.color.white))
                    refreshPublishedAdapter()
                }
            }
            1->{
                if (!(view?.getTextSelectTwoView()?.tag as Boolean)) {
                    view?.getTextSelectTwoView()?.tag = true
                    view?.getTextSelectTwoView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectTwoView()?.setTextColor(ResourceUtil.getColorId(R.color.white))
                    refreshAuditAdapter()
                }
            }
            2->{
                if (!(view?.getTextSelectThreeView()?.tag as Boolean)) {
                    view?.getTextSelectThreeView()?.tag = true
                    view?.getTextSelectThreeView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectThreeView()?.setTextColor(ResourceUtil.getColorId(R.color.white))
                    refreshAlreadyDownAdapter()
                }
            }
        }
    }

    /**
     * 刷新已发布配器
     */
    override fun refreshPublishedAdapter() {
        var publishedAdapter = FullPublishedAdapter(R.layout.code_recycler_full_published_item, getPositionData("0","1"))
        view?.getRecyclerFullTimeView()!!.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view?.getRecyclerFullTimeView()!!.adapter = publishedAdapter
        publishedAdapter.setOnItemChildClickListener(this)
        publishedAdapter?.setOnItemClickListener({
            adapter, view, position ->onPublishedClickItem(showPositions[position].id)
        })
    }

    /**
     * 刷新审核中
     */
    override fun refreshAuditAdapter() {
        var auditAdapter = PartAuditAdapter(R.layout.code_recycler_published_item, getPositionData("0","0"))
        view?.getRecyclerFullTimeView()!!.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view?.getRecyclerFullTimeView()!!.adapter = auditAdapter
        auditAdapter.setOnItemChildClickListener(this)
        auditAdapter?.setOnItemClickListener({
            adapter, view, position ->onPublishedClickItem(showPositions[position].id)
        })
    }

    override fun refreshAlreadyDownAdapter() {
        var alreadyAdapter = PartAlreadyDownAdapter(R.layout.code_recycler_full_published_item, getPositionData("0","3"))
        view?.getRecyclerFullTimeView()!!.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view?.getRecyclerFullTimeView()!!.adapter = alreadyAdapter
        alreadyAdapter.setOnItemChildClickListener(this)
        alreadyAdapter?.setOnItemClickListener({
            adapter, view, position ->onPublishedClickItem(showPositions[position].id)
        })
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view?.id){
            R.id.tv_release_status->{
                onPublishClickRelase()
            }
        }
    }

    fun onPublishClickRelase() {
    }

    fun onPublishedClickItem(id : Int) {
        var intent = Intent(activity!!,ManageFullTimeInfoActivity::class.java)
        intent.putExtra("manageType",1)
        intent.putExtra("id",id)
        activity?.startActivity(intent)
    }

    fun getPositionData(type : String, verify : String) : ArrayList<Position>{
        showPositions.clear()
        for (i in 0..this.positions.size - 1){
            var position = this.positions[i]
            if (type == position.type && verify == position.verify){
                showPositions.add(position)
            }
        }
        return showPositions
    }
}