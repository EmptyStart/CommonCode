package com.xx.code.presenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.amap.api.maps.model.LatLng
import com.xx.code.R
import com.xx.code.responsebody.*
import com.xx.code.bean.JobType
import com.xx.code.constracts.IManageFullTimeInfoPresent
import com.xx.code.constracts.IManageFullTimeInfoView
import com.xx.code.viewmodels.ManageFullTimeInfoModelImpl
import com.xx.code.utils.FullTimeUtils
import com.xx.code.views.activitys.ManageSetActivity
import com.xx.code.views.adapter.full.ManageFullInfoJobAdapter
import com.xx.code.views.adapter.full.ManageFullInfoRequireAdapter
import com.xx.common.base.IDataListener
import com.xx.common.https.CommonUrls
import com.xx.common.utils.GsonUtils
import com.xx.common.utils.ResourceUtil
import org.json.JSONObject

/**
 * Created by Administrator on 2018/8/29/029.
 */
class ManageFullTimeInfoPresenterImpl : IManageFullTimeInfoPresent {
    var TAG = "ManageFullTimeInfoPresenterImpl"
    var jobs = ArrayList<JobType>()
    var work_Experiences = ArrayList<Work_Experience>()

    var view : IManageFullTimeInfoView
    var jobAdapter : ManageFullInfoJobAdapter? = null
    var requireAdapter : ManageFullInfoRequireAdapter? = null
    var infoActivity : Activity?= null
    var position : Position? = null                                                                 //职位详情
    var count : Count? = null                                                                       //统计数量

    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { ManageFullTimeInfoModelImpl() }

    constructor(view : IManageFullTimeInfoView){
        this.view = view
        infoActivity = view.getInfoActivity()
        fullData()
    }

    private fun fullData(){
        mViewMode.addFullTimeListener(object : IDataListener{
            override fun successData(success: String) {
                var pJson = JSONObject(success).getJSONObject("position")
                var ops_count = JSONObject(success).getJSONObject("ops_count")
                if (pJson != null)
                    position = GsonUtils.getGson.fromJson(pJson.toString(), Position::class.java)
                if (ops_count != null){
                    count  = GsonUtils.getGson.fromJson(ops_count.toString(), Count::class.java)
                }
                //LogUtils.i(TAG,"position="+position.toString())
                //LogUtils.i(TAG,"count="+count.toString())
                initJob()
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
               if (this.view.getManageTypeInt() == 2){
                   toActivity(1)
               }else{
                   toActivity(4)
               }
           }
           R.id.ll_manage_two->{
               if (this.view.getManageTypeInt() == 2){
                   toActivity(2)
               }else{
                   toActivity(5)
               }
           }
           R.id.ll_manage_three->{
               if (this.view.getManageTypeInt() == 2){
                   toActivity(3)
               }else{
                   toActivity(6)
               }
           }
           R.id.ll_manage_four->{
               toActivity(7)
           }
           R.id.iv_actionbar_cancle->{
               infoActivity?.finish()
           }
       }
    }

    fun toActivity(manageSetType : Int){
        if (position != null) {
            var intent = Intent(infoActivity, ManageSetActivity::class.java)
            intent.putExtra("manageSetType", manageSetType)
            intent.putExtra("id", position?.id)
            infoActivity?.startActivity(intent)
        }
    }

    fun initJob(){
        if (position != null){
            var time = ""
            var salary =  ""
            if (view.getManageTypeInt() == 1) {
                time  = FullTimeUtils.getWeeks(position?.interview_day) + "\n" + FullTimeUtils.getTimes(position?.interview_time)
                salary = position?.salary_begin.toString()+"-"+position?.salary_end+"元"
            }else{
                if (position?.on_site_time != null)
                    time = position?.on_site_time!!.replace("T"," ")
                if (position?.salary_unit != null){
                    salary = position?.salary_begin.toString() +"元/"+ResourceUtil.getStringArrayValue(R.array.cycle_time,position?.salary_unit)
                }
            }
            view.setTextAddressTime(position?.province,position?.city,position?.region,position?.address, time)
            view.setTextCompanyInfo(position?.name, position?.skill_set)
            view.getSalaryView().text = salary

            if (position?.latitude != null && position?.longitude != null)
                view.setAddressLocation(LatLng(position?.latitude!!, position!!.longitude))
            else
                view.setAddressLocation(LatLng(0.0, 0.0))

            configInfo()
        }

        if (!TextUtils.isEmpty(position?.descriptions))
            view.getTextRequireView().text = position?.descriptions!!

        view.getManageNumOneView().text = count?.apply_count.toString()
        if (view.getManageTypeInt() == 1){
            view.getManageNumTwoView().text = count?.interview_count.toString()
            view.getManageNumThreeView().text = count?.onboard_count.toString()
            view.getManageNumFourView().text = count?.payment_count.toString()
        }else{
            view.getManageNumTwoView().text = count?.onsite_count.toString()
            view.getManageNumThreeView().text = count?.payment_count.toString()
        }
    }

    fun configInfo(){
        view.showLoading()
        mViewMode.addConfigInfo(object : IDataListener{
            override fun successData(success: String) {
                view.hideLoading()
                work_Experiences.clear()
                var arrays = JSONObject(success).getJSONArray("results")
                for (i in 0..arrays.length() - 1){
                    work_Experiences.add( GsonUtils.getGson.fromJson(arrays[i].toString(), Work_Experience::class.java))
                }
                //LogUtils.i(TAG,"WORK_EXPERIENCE =="+success)
                //LogUtils.i(TAG,"work_Experiences =="+work_Experiences.size)
                jobData()
            }

            override fun errorData(error: String) {
                view.hideLoading()
                jobData()
            }

            override fun requestData(): Map<String, String>? {
                return mapOf(Pair("type", "WORK_EXPERIENCE"))
            }
        })
    }

    fun getJobTime(exp_year_beg : Int,exp_year_end : Int) : String{
        var jobTime = exp_year_beg.toString()+"-"+exp_year_end
        for (i in 0..work_Experiences.size - 1){
            if (jobTime.equals(work_Experiences[i].key)){
                jobTime = work_Experiences[i].value
                break
            }
        }
        return jobTime
    }

    fun jobData(){
        if (position != null ) {
            var pName = "未知"
            if (position?.career_type != null) {
                pName = JSONObject(position?.career_type.toString()).getString("name")
            }
            jobs.add(JobType("职位类型", pName))
            jobs.add(JobType("最低学历", getEducation(position?.education!!)))
            jobs.add(JobType("工作经验", getJobTime(position?.exp_year_beg!!, position?.exp_year_end!!)))
            onRefreshJobAdapter()
        }
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

    fun onFullInfoJodClickItem() {
    }

    fun onFullInfoRequireClickItem() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }

    fun getEducation(education : Int) : String{
        var str = ""
        when(education){
            0->{
                str = "不限"
            }
            1->{
                str = "初中及以下"
            }
            2->{
                str = "中专/中技"
            }
            3->{
                str = "高中"
            }
            4->{
                str = "大专"
            }
            5->{
                str = "本科"
            }
            6->{
                str = "硕士"
            }
            7->{
                str = "博士"
            }
        }
        return  str
    }

    fun getApplysStatus(status : String) : String{
        var str = ""
        when(status){
            "00"->{
                str = "申请"
            }
            "01"->{
                str = "HR同意"
            }
            "02"->{
                str = "HR拒绝"
            }
            "10"->{
                str = "到场"
            }
            "11"->{
                str = "HR到场确认"
            }
            "12"->{
                str = "HR到场未确认"
            }
            "00"->{
                str = "硕士"
            }
            "00"->{
                str = "博士"
            }
        }
        return  str
    }
}