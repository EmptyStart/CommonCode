package com.tima.code.timapresenter

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tima.code.R
import com.tima.code.timaconstracts.IManageSetPresent
import com.tima.code.timaconstracts.IManageSetView
import com.tima.code.views.adapter.manage.ManageSetAdapter
import com.tima.common.utils.ResourceUtil
import kotlinx.android.synthetic.main.code_activity_manage_set.*
import kotlinx.android.synthetic.main.code_three_select_top.*

/**
 * Created by Administrator on 2018/9/3/003.
 */
class ManageSetPresenterImpl : IManageSetPresent ,ManageSetAdapter.OnManageSetListener{


    var view : IManageSetView
    var activity : Activity
    var manageSetAdapter : ManageSetAdapter? = null

    constructor(manageSetView : IManageSetView){
        this.view = manageSetView
        activity = view.getManageActivity()
        onRefreshAdapter()
    }
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.iv_search->{
                
            }
            R.id.tv_click_one->{

            }
            R.id.tv_click_two->{

            }
            R.id.tv_select_one->{
                toSelect(0)
            }
            R.id.tv_select_two->{
                toSelect(1)
            }
            R.id.tv_select_three->{
                toSelect(2)
            }
            R.id.iv_total_check->{

            }
            R.id.iv_actionbar_cancle->{
                activity.finish()
            }
        }
    }

    override fun toSelect(position: Int) {
        if ( view.getTextSelectOneView().tag as Boolean) {
            view.getTextSelectOneView().tag = false
            view.getTextSelectOneView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view.getTextSelectOneView().setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if ( view.getTextSelectTwoView().tag as Boolean) {
            view.getTextSelectTwoView().tag = false
            view.getTextSelectTwoView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view.getTextSelectTwoView().setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if ( view.getTextSelectThreeView().tag as Boolean) {
            view.getTextSelectThreeView().tag = false
            view.getTextSelectThreeView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view.getTextSelectThreeView().setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        when(position){
            0->{
                if (!(view.getTextSelectOneView().tag as Boolean)) {
                    view.getTextSelectOneView().tag = true
                    view.getTextSelectOneView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view.getTextSelectOneView().setTextColor(ResourceUtil.getColorId(R.color.white))
                }
            }
            1->{
                if (!(view.getTextSelectTwoView().tag as Boolean)) {
                    view.getTextSelectTwoView().tag = true
                    view.getTextSelectTwoView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view.getTextSelectTwoView().setTextColor(ResourceUtil.getColorId(R.color.white))
                }
            }
            2->{
                if (!(view.getTextSelectThreeView().tag as Boolean)) {
                    view.getTextSelectThreeView().tag = true
                    view.getTextSelectThreeView().setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view.getTextSelectThreeView().setTextColor(ResourceUtil.getColorId(R.color.white))
                }
            }
        }
    }


    override fun onRefreshAdapter() {
        if (manageSetAdapter == null) {
            manageSetAdapter = ManageSetAdapter(activity, this)
            view.getManageSetRecyclerView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            view.getManageSetRecyclerView().adapter = manageSetAdapter
        }else{
            manageSetAdapter?.notifyDataSetChanged()
        }
    }

    override fun onManageSetOneClick() {
    }

    override fun onManageSetTwoClick() {
    }

    override fun onManageSetCheckClick() {
    }
}