package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tima.code.R
import com.tima.code.ResponseBody.Position
import com.tima.code.timaconstracts.IPartTimePresent
import com.tima.code.timaconstracts.IPartTimeView
import com.tima.code.timaviewmodels.PartTimeViewModelImpl
import com.tima.code.views.activitys.ManageFullTimeInfoActivity
import com.tima.code.views.adapter.part.PartAlreadyDownAdapter
import com.tima.code.views.adapter.part.PartAuditAdapter
import com.tima.code.views.adapter.part.PartPublishedAdapter
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.LogUtils
import com.tima.common.utils.ResourceUtil
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * 管理-兼职
 * Created by Administrator on 2018/8/28/028.
 */
class PartTimePresenterImpl : IPartTimePresent {
    var TAG = "PartTimePresenterImpl"
    var activity: Activity? = null
    var view: IPartTimeView? = null
    var positions = ArrayList<Position>()
    var currentPosition = 0

    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { PartTimeViewModelImpl() }

    constructor(view: IBaseViews?) {
        this.view = view as IPartTimeView?
        init()
    }

    override fun init() {
        activity = view?.getPartActivity()
        fullData()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_select_one -> {
                currentPosition = 0
            }
            R.id.tv_select_two -> {
                currentPosition = 1
            }
            R.id.tv_select_three -> {
                currentPosition = 2
            }
        }
        toSelect(currentPosition)
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

    fun fullData(){
        view?.showLoading()
        mViewMode.addPartTimeListener(object : IDataListener {
            override fun successData(success: String) {
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

    /**
     * 刷新已发布配器
     */
    override fun refreshPublishedAdapter() {
        var publishedAdapter = PartPublishedAdapter(R.layout.code_recycler_published_item, getPositionData("0","0"))
        view?.getRecyclerPartTimeView()!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view?.getRecyclerPartTimeView()!!.adapter = publishedAdapter
        publishedAdapter.setOnItemClickListener({
            adapter, view, position ->publishedClick(position)
        })
    }

    /**
     * 刷新审核中
     */
    override fun refreshAuditAdapter() {
        var auditAdapter = PartAuditAdapter(R.layout.code_recycler_published_item, getPositionData("0","0"))
        view?.getRecyclerPartTimeView()!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view?.getRecyclerPartTimeView()!!.adapter = auditAdapter
        auditAdapter.setOnItemClickListener({
            adapter, view, position ->onAuditClick(position)
        })
    }

    /**
     * 刷新已下架适配器
     */
    override fun refreshAlreadyDownAdapter() {
        var alreadyDownAdapter = PartAlreadyDownAdapter(R.layout.code_recycler_published_item, getPositionData("0","3"))
        view?.getRecyclerPartTimeView()!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view?.getRecyclerPartTimeView()!!.adapter = alreadyDownAdapter
        alreadyDownAdapter.setOnItemClickListener({
            adapter, view, position ->onAlreadyDownClick(position)
        })
    }

    fun publishedClick(position : Int){
        activity?.toast("点击----"+position)
        if (activity != null) {
            Toast.makeText(activity, "activity不为空", Toast.LENGTH_SHORT).show()
            var intent = Intent(activity!!, ManageFullTimeInfoActivity::class.java)
            intent.putExtra("manageType", 2)
            activity?.startActivity(intent)
        } else {
            Toast.makeText(activity, "activity为空null", Toast.LENGTH_SHORT).show()
        }
    }

    fun getPositionData(type : String, verify : String) : ArrayList<Position>{
        var positions = ArrayList<Position>()
        for (i in 0..this.positions.size - 1){
            var position = this.positions[i]
            if (type == position.type && verify == position.verify){
                positions.add(position)
            }
        }
        return positions
    }

    fun onAuditClick(position : Int) {
        Toast.makeText(activity, "点击审核中", Toast.LENGTH_SHORT).show()
    }

     fun onAlreadyDownClick(position : Int) {
        Toast.makeText(activity, "点击已下架", Toast.LENGTH_SHORT).show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}
