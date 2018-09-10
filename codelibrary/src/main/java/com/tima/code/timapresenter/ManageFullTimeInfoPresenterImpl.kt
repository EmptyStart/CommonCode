package com.tima.code.timapresenter

import android.app.Activity
import android.app.job.JobInfo
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tima.code.R
import com.tima.code.ResponseBody.LoginResponseBody
import com.tima.code.ResponseBody.Position
import com.tima.code.bean.JobType
import com.tima.code.timaconstracts.IManageFullTimeInfoPresent
import com.tima.code.timaconstracts.IManageFullTimeInfoView
import com.tima.code.timaviewmodels.ManageFullTimeInfoModelImpl
import com.tima.code.views.activitys.ManageSetActivity
import com.tima.code.views.adapter.full.ManageFullInfoJobAdapter
import com.tima.code.views.adapter.full.ManageFullInfoRequireAdapter
import com.tima.common.base.IDataListener
import com.tima.common.https.CommonUrls
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.ResourceUtil
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Administrator on 2018/8/29/029.
 */
class ManageFullTimeInfoPresenterImpl : IManageFullTimeInfoPresent {
    var jobs = ArrayList<JobType>()
    var requires = ArrayList<String>()
    var view : IManageFullTimeInfoView
    var jobAdapter : ManageFullInfoJobAdapter? = null
    var requireAdapter : ManageFullInfoRequireAdapter? = null
    var infoActivity : Activity?= null

    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { ManageFullTimeInfoModelImpl() }


    constructor(view : IManageFullTimeInfoView){
        this.view = view
        infoActivity = view?.getInfoActivity()
        initJob()
        onRefreshJobAdapter()
        onRefreshRequireAdapter()
        fullData()
    }

    private fun fullData(){
        mViewMode.addFullTimeListener(object : IDataListener{
            override fun successData(success: String) {

            }

            override fun errorData(error: String) {

            }

            override fun requestData(): Map<String, String>? {
                return null
            }
        },CommonUrls.manageFull+view.getPositionId()+"/")
    }

    override fun onClick(view: View?) {
       when(view?.id){
           R.id.ll_manage_one->{
               toSelect(0)
               toActivity(4)
           }
           R.id.ll_manage_two->{
               toSelect(1)
               toActivity(5)
           }
           R.id.ll_manage_three->{
               toSelect(2)
               toActivity(6)
           }
           R.id.ll_manage_four->{
               toSelect(3)
               toActivity(7)
           }
           R.id.ll_manage_part_one->{
               toSelect(0)
               toActivity(1)
           }
           R.id.ll_manage_part_two->{
               toSelect(1)
               toActivity(2)
           }
           R.id.ll_manage_part_three->{
               toSelect(2)
               toActivity(3)
           }
       }
    }

    fun toActivity(manageSetType : Int){
        var intent = Intent(infoActivity,ManageSetActivity::class.java)
        intent.putExtra("manageSetType",manageSetType)
        infoActivity?.startActivity(intent)
    }

    fun initJob(){
        jobs.add(JobType("职位类型",null))
        jobs.add(JobType("最低学历",null))
        jobs.add(JobType("工作类型",null))

        requires.add("兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市")
        requires.add("兼职短发鞍山市兼")
        requires.add("兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市兼职短发鞍山市")
    }

    fun toSelect(position : Int){
        var gray = ResourceUtil.getColorId(R.color.text_gray)
        var white = ResourceUtil.getColorId(R.color.white)
        var bgGray = R.drawable.radius_white_solid_gray
        var bgRed = R.drawable.radius_white_solid_red

        view?.getManageOneView()?.tag = if (position == 0) true else false
        view?.getManageOneView()?.setBackgroundResource( if (position == 0) bgRed else bgGray )
        view?.getManageNumOneView()?.setTextColor(if (position == 0) white else gray)
        view?.getManageTitleOneView()?.setTextColor(if (position == 0) white else gray)

        view?.getManageTwoView()?.tag = if (position == 1) true else false
        view?.getManageTwoView()?.setBackgroundResource( if (position == 1) bgRed else bgGray)
        view?.getManageNumTwoView()?.setTextColor(if (position == 1) white else gray)
        view?.getManageTitleTwoView()?.setTextColor(if (position == 1) white else gray)

        view?.getManageThreeView()?.tag = if (position == 2) true else false
        view?.getManageThreeView()?.setBackgroundResource( if (position == 2) bgRed else bgGray)
        view?.getManageNumThreeView()?.setTextColor(if (position == 2) white else gray)
        view?.getManageTitleThreeView()?.setTextColor(if (position == 2) white else gray)

        view?.getManageFourView()?.tag = if (position == 3) true else false
        view?.getManageFourView()?.setBackgroundResource( if (position == 3) bgRed else bgGray)
        view?.getManageNumFourView()?.setTextColor(if (position == 3) white else gray)
        view?.getManageTitleFourView()?.setTextColor(if (position == 3) white else gray)
    }

    override fun onRefreshJobAdapter() {
        if (jobAdapter == null){
            jobAdapter = ManageFullInfoJobAdapter(R.layout.code_recycler_manage_full_info_item, jobs)
            view?.getRecyclerJobDescribeView().layoutManager = LinearLayoutManager(infoActivity, LinearLayoutManager.VERTICAL,false)
            view?.getRecyclerJobDescribeView().adapter = jobAdapter
            jobAdapter?.setOnItemClickListener({
                adapter, view, position ->onFullInfoJodClickItem()
            })
        }else{
            jobAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRefreshRequireAdapter() {
        if (requireAdapter == null){
            requireAdapter = ManageFullInfoRequireAdapter(R.layout.code_recycler_manage_full_info_require_item, requires)
            //requireAdapter = ManageFullInfoRequireAdapter(infoActivity!!,this,requires)
            view?.getRecyclerRequireView().layoutManager = LinearLayoutManager(infoActivity, LinearLayoutManager.VERTICAL,false)
            view?.getRecyclerRequireView().adapter = requireAdapter
            requireAdapter?.setOnItemClickListener({
                adapter, view, position ->onFullInfoRequireClickItem()
            })
        }else{
            requireAdapter!!.notifyDataSetChanged()
        }
    }

    fun onFullInfoJodClickItem() {
    }

    fun onFullInfoRequireClickItem() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}