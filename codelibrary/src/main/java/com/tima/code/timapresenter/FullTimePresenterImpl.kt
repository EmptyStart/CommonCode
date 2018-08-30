package com.tima.code.timapresenter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tima.code.R
import com.tima.code.timaconstracts.IFullTimePresent
import com.tima.code.timaconstracts.IFullTimeView
import com.tima.code.timaviewmodels.FullTimeViewModelImpl
import com.tima.code.views.activitys.ManageFullTimeInfoActivity
import com.tima.code.views.adapter.full.FullPublishedAdapter
import com.tima.common.base.IBaseViews
import com.tima.common.utils.ColorIdUtil

/**
 * 管理-全职
 * Created by Administrator on 2018/8/28/028.
 */
class FullTimePresenterImpl : IFullTimePresent, FullPublishedAdapter.OnPublishedListener{

    var activity : Activity? = null
    var view: IFullTimeView? = null
    var viewMode: FullTimeViewModelImpl? = null

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
            view?.getTextSelectOneView()?.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectOneView()?.setTextColor(ColorIdUtil.getColorId(R.color.text_black_gray))
        }
        if ( view?.getTextSelectTwoView()?.tag as Boolean) {
            view?.getTextSelectTwoView()?.tag = false
            view?.getTextSelectTwoView()?.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectTwoView()?.setTextColor(ColorIdUtil.getColorId(R.color.text_black_gray))
        }
        if ( view?.getTextSelectThreeView()?.tag as Boolean) {
            view?.getTextSelectThreeView()?.tag = false
            view?.getTextSelectThreeView()?.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectThreeView()?.setTextColor(ColorIdUtil.getColorId(R.color.text_black_gray))
        }
        when(position){
            0->{
                if (!(view?.getTextSelectOneView()?.tag as Boolean)) {
                    view?.getTextSelectOneView()?.tag = true
                    view?.getTextSelectOneView()?.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectOneView()?.setTextColor(ColorIdUtil.getColorId(R.color.white))
                    refreshPublishedAdapter()
                }
            }
            1->{
                if (!(view?.getTextSelectTwoView()?.tag as Boolean)) {
                    view?.getTextSelectTwoView()?.tag = true
                    view?.getTextSelectTwoView()?.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectTwoView()?.setTextColor(ColorIdUtil.getColorId(R.color.white))
                    refreshAuditAdapter()
                }
            }
            2->{
                if (!(view?.getTextSelectThreeView()?.tag as Boolean)) {
                    view?.getTextSelectThreeView()?.tag = true
                    view?.getTextSelectThreeView()?.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectThreeView()?.setTextColor(ColorIdUtil.getColorId(R.color.white))
                    refreshAlreadyDownAdapter()
                }
            }
        }
    }

    /**
     * 刷新已发布配器
     */
    override fun refreshPublishedAdapter() {
        var publishedAdapter = FullPublishedAdapter(activity!!, this)
        view?.getRecyclerFullTimeView()!!.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view?.getRecyclerFullTimeView()!!.adapter = publishedAdapter
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

    override fun onPublishClickRelase() {
    }

    override fun onPublishedClickItem() {
        var intent = Intent(activity!!,ManageFullTimeInfoActivity::class.java)
        intent.putExtra("manageType",1)
        activity?.startActivity(intent)
    }
}