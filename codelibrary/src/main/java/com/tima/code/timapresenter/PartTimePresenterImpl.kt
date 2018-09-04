package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.tima.code.R
import com.tima.code.timaconstracts.IPartTimePresent
import com.tima.code.timaconstracts.IPartTimeView
import com.tima.code.timaviewmodels.PartTimeViewModelImpl
import com.tima.code.views.activitys.ManageFullTimeInfoActivity
import com.tima.code.views.adapter.part.PartAlreadyDownAdapter
import com.tima.code.views.adapter.part.PartAuditAdapter
import com.tima.code.views.adapter.part.PartPublishedAdapter
import com.tima.common.base.IBaseViews
import com.tima.common.utils.ResourceUtil

/**
 *
 * Created by Administrator on 2018/8/28/028.
 */
class PartTimePresenterImpl : IPartTimePresent, PartPublishedAdapter.OnPublishedListener, PartAuditAdapter.OnAuditListener, PartAlreadyDownAdapter.OnAlreadyDownListener {

    var activity: Activity? = null
    var view: IPartTimeView? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { PartTimeViewModelImpl() }

    constructor(view: IBaseViews?) {
        this.view = view as IPartTimeView?
        init()
    }

    override fun init() {
        activity = view?.getPartActivity()
        view?.getTextSelectOneView()?.tag = false
        view?.getTextSelectTwoView()?.tag = false
        view?.getTextSelectThreeView()?.tag = false

        toSelect(0)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_select_one -> {
                toSelect(0)
            }
            R.id.tv_select_two -> {
                toSelect(1)
            }
            R.id.tv_select_three -> {
                toSelect(2)
            }
        }
    }

    override fun toSelect(position: Int) {
        if (view?.getTextSelectOneView()?.tag as Boolean) {
            view?.getTextSelectOneView()?.tag = false
            view?.getTextSelectOneView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectOneView()?.setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if (view?.getTextSelectTwoView()?.tag as Boolean) {
            view?.getTextSelectTwoView()?.tag = false
            view?.getTextSelectTwoView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectTwoView()?.setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        if (view?.getTextSelectThreeView()?.tag as Boolean) {
            view?.getTextSelectThreeView()?.tag = false
            view?.getTextSelectThreeView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_linebc))
            view?.getTextSelectThreeView()?.setTextColor(ResourceUtil.getColorId(R.color.text_black_gray))
        }
        when (position) {
            0 -> {
                if (!(view?.getTextSelectOneView()?.tag as Boolean)) {
                    view?.getTextSelectOneView()?.tag = true
                    view?.getTextSelectOneView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectOneView()?.setTextColor(ResourceUtil.getColorId(R.color.white))
                    refreshPublishedAdapter()
                }
            }
            1 -> {
                if (!(view?.getTextSelectTwoView()?.tag as Boolean)) {
                    view?.getTextSelectTwoView()?.tag = true
                    view?.getTextSelectTwoView()?.setBackgroundColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    view?.getTextSelectTwoView()?.setTextColor(ResourceUtil.getColorId(R.color.white))
                    refreshAuditAdapter()
                }
            }
            2 -> {
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
        var publishedAdapter = PartPublishedAdapter(activity!!, this)
        view?.getRecyclerPartTimeView()!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view?.getRecyclerPartTimeView()!!.adapter = publishedAdapter
    }

    /**
     * 刷新审核中
     */
    override fun refreshAuditAdapter() {
        var auditAdapter = PartAuditAdapter(activity!!, this)
        view?.getRecyclerPartTimeView()!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view?.getRecyclerPartTimeView()!!.adapter = auditAdapter
    }

    override fun refreshAlreadyDownAdapter() {
        var alreadyDownAdapter = PartAlreadyDownAdapter(activity!!, this)
        view?.getRecyclerPartTimeView()!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view?.getRecyclerPartTimeView()!!.adapter = alreadyDownAdapter
    }

    override fun onPublishedClick() {
        if (activity != null) {
            Toast.makeText(activity, "activity不为空", Toast.LENGTH_SHORT).show()

            var intent = Intent(activity!!, ManageFullTimeInfoActivity::class.java)
            intent.putExtra("manageType", 2)
            activity?.startActivity(intent)
        } else {
            Toast.makeText(activity, "activity为空null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAuditClick() {
        Toast.makeText(activity, "点击审核中", Toast.LENGTH_SHORT).show()
    }

    override fun onAlreadyDownClick() {
        Toast.makeText(activity, "点击已下架", Toast.LENGTH_SHORT).show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}