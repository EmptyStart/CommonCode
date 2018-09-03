package com.tima.code.timapresenter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tima.code.R
import com.tima.code.bean.jobInfo
import com.tima.code.timaconstracts.IManageFullTimeInfoPresent
import com.tima.code.timaconstracts.IManageFullTimeInfoView
import com.tima.code.views.activitys.ManageSetActivity
import com.tima.code.views.adapter.full.ManageFullInfoJobAdapter
import com.tima.code.views.adapter.full.ManageFullInfoRequireAdapter
import com.tima.common.utils.ResourceUtil

/**
 * Created by Administrator on 2018/8/29/029.
 */
class ManageFullTimeInfoPresenterImpl : IManageFullTimeInfoPresent ,ManageFullInfoJobAdapter.OnFullInfoJodListener,ManageFullInfoRequireAdapter.OnFullInfoRequireListener{


    var jobs = ArrayList<jobInfo>()
    var requires = ArrayList<String>()
    var view : IManageFullTimeInfoView
    var jobAdapter : ManageFullInfoJobAdapter? = null
    var requireAdapter : ManageFullInfoRequireAdapter? = null
    var infoActivity : Activity?= null

    constructor(view : IManageFullTimeInfoView){
        this.view = view
        infoActivity = view?.getInfoActivity()
        initJob()
        onRefreshJobAdapter()
        onRefreshRequireAdapter()
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
        jobs.add(jobInfo("职位类型",null))
        jobs.add(jobInfo("最低学历",null))
        jobs.add(jobInfo("工作类型",null))

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
            jobAdapter = ManageFullInfoJobAdapter(infoActivity!!,this,jobs)
            view?.getRecyclerJobDescribeView().layoutManager = LinearLayoutManager(infoActivity, LinearLayoutManager.VERTICAL,false)
            view?.getRecyclerJobDescribeView().adapter = jobAdapter
        }else{
            jobAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRefreshRequireAdapter() {
        if (requireAdapter == null){
            requireAdapter = ManageFullInfoRequireAdapter(infoActivity!!,this,requires)
            view?.getRecyclerRequireView().layoutManager = LinearLayoutManager(infoActivity, LinearLayoutManager.VERTICAL,false)
            view?.getRecyclerRequireView().adapter = requireAdapter
        }else{
            requireAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onFullInfoJodClickItem() {
    }

    override fun onFullInfoRequireClickItem() {
    }
}