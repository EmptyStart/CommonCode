package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tima.code.R
import com.tima.code.timaconstracts.IManageSetPresent
import com.tima.code.timaconstracts.IManageSetView
import com.tima.code.timaviewmodels.ManageSetViewModelImpl
import com.tima.code.views.adapter.manage.ManageSetAdapter
import com.tima.common.utils.ResourceUtil
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2018/9/3/003.
 */
class ManageSetPresenterImpl : IManageSetPresent,BaseQuickAdapter.OnItemChildClickListener {

    var view: IManageSetView
    var activity: Activity
    var manageSetAdapter: ManageSetAdapter? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { ManageSetViewModelImpl() }

    constructor(manageSetView: IManageSetView) {
        this.view = manageSetView
        activity = view.getManageActivity()
        onRefreshAdapter()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_search -> {

            }
            R.id.tv_click_one -> {

            }
            R.id.tv_click_two -> {

            }
            R.id.tv_select_one -> {
                toSelect(0)
            }
            R.id.tv_select_two -> {
                toSelect(1)
            }
            R.id.tv_select_three -> {
                toSelect(2)
            }
            R.id.iv_total_check -> {

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


    override fun onRefreshAdapter() {
        if (manageSetAdapter == null) {
            var datas = listOf<String>("11","","","","","","","")
            manageSetAdapter = ManageSetAdapter(R.layout.code_recycler_manage_set_item, datas)
            //manageSetAdapter = ManageSetAdapter(activity, this)
            view.getManageSetRecyclerView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            view.getManageSetRecyclerView().adapter = manageSetAdapter
            manageSetAdapter?.setOnItemChildClickListener(this)
        } else {
            manageSetAdapter?.notifyDataSetChanged()
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.iv_manage_set_check->{
                activity.toast("点击----iv_manage_set_check")
            }
            R.id.tv_manage_set_two->{
                activity.toast("点击----tv_manage_set_two")
            }
            R.id.tv_manage_set_one->{
                activity.toast("点击----tv_manage_set_two")
            }
        }
    }

    fun onManageSetOneClick() {
    }

    fun onManageSetTwoClick() {
    }

    fun onManageSetCheckClick() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}