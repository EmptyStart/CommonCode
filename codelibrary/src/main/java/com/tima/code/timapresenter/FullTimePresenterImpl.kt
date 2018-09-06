package com.tima.code.timapresenter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tima.code.R
import com.tima.code.ResponseBody.Position
import com.tima.code.timaconstracts.IFullTimePresent
import com.tima.code.timaconstracts.IFullTimeView
import com.tima.code.timaviewmodels.FullTimeViewModelImpl
import com.tima.code.timaviewmodels.ManageFullTimeInfoModelImpl
import com.tima.code.views.activitys.ManageFullTimeInfoActivity
import com.tima.code.views.adapter.full.FullPublishedAdapter
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

    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { FullTimeViewModelImpl() }

    constructor(view: IBaseViews?) {
        this.view = view as IFullTimeView?
        viewMode = FullTimeViewModelImpl()
        init()
    }

    override fun init() {
        activity = view?.getFullActivity()
        view?.getTextSelectOneView()?.tag = false
        view?.getTextSelectTwoView()?.tag = false
        view?.getTextSelectThreeView()?.tag = false

        toSelect(0)
        fullData()
    }


    private fun fullData(){
        view?.showLoading()
        mViewMode.addFullTimeListener(object : IDataListener {
            override fun successData(success: String) {
                LogUtils.i(TAG,"返回发布职位=="+success)
                var arrays = JSONObject(success).getJSONArray("results")
                for (i in 0..arrays.length() - 1){
                    val position = GsonUtils.getGson.fromJson(arrays[i].toString(), Position::class.java)
                    positions.add(position)
                }
                view?.hideLoading()
                refreshPublishedAdapter()
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
                toSelect(0)
            }
            R.id.tv_select_two->{
                toSelect(1)
            }
            R.id.tv_select_three->{
                toSelect(2)
            }
        }
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
                    //refreshPublishedAdapter()
                }
            }
            1->{
                if (!(view?.getTextSelectTwoView()?.tag as Boolean)) {
                    view?.getTextSelectTwoView()?.tag = true
                    view?.getTextSelectTwoView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectTwoView()?.setTextColor(ResourceUtil.getColorId(R.color.white))
                    //refreshAuditAdapter()
                }
            }
            2->{
                if (!(view?.getTextSelectThreeView()?.tag as Boolean)) {
                    view?.getTextSelectThreeView()?.tag = true
                    view?.getTextSelectThreeView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectThreeView()?.setTextColor(ResourceUtil.getColorId(R.color.white))
                    //refreshAlreadyDownAdapter()
                }
            }
        }
    }

    /**
     * 刷新已发布配器
     */
    override fun refreshPublishedAdapter() {
        var publishedAdapter = FullPublishedAdapter(R.layout.code_recycler_full_published_item, positions)
        //var publishedAdapter = FullPublishedAdapter(activity!!, this,positionInfos)
        view?.getRecyclerFullTimeView()!!.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view?.getRecyclerFullTimeView()!!.adapter = publishedAdapter
        publishedAdapter.setOnItemChildClickListener(this)
        publishedAdapter?.setOnItemClickListener({
            adapter, view, position ->onPublishedClickItem()
        })
    }

    /**
     * 刷新审核中
     */
    override fun refreshAuditAdapter() {
        /*var auditAdapter = PartAuditAdapter(activity!!,this)
        view?.getRecyclerPartTimeView()!!.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view?.getRecyclerPartTimeView()!!.adapter = auditAdapter*/
    }

    override fun refreshAlreadyDownAdapter() {
        /*var alreadyDownAdapter = PartAlreadyDownAdapter(activity!!,this)
        view?.getRecyclerPartTimeView()!!.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view?.getRecyclerPartTimeView()!!.adapter = alreadyDownAdapter*/
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

    fun onPublishedClickItem() {
        var intent = Intent(activity!!,ManageFullTimeInfoActivity::class.java)
        intent.putExtra("manageType",1)
        activity?.startActivity(intent)
    }

}