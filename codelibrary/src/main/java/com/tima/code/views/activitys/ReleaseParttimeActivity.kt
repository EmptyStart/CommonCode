package com.tima.code.views.activitys

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.ResponseBody.CareerTypeResult
import com.tima.code.ResponseBody.LocationBean
import com.tima.code.ResponseBody.ReleasePopData
import com.tima.code.timaconstracts.IReleaseTimeView
import com.tima.code.timaconstracts.OnSelectListener
import com.tima.code.timapresenter.ReleaseTimePresenterImpl
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_release_parttime.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/29
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.releaseparttime)
class ReleaseParttimeActivity : AbstractAddressAndMapActivity(), View.OnClickListener, IReleaseTimeView {
    private var adapter: BaseQuickAdapter<ReleasePopData, BaseViewHolder>? = null
    private var popSa2: PopupWindow? = null
    val releasePopData = arrayListOf<ReleasePopData>()
    override fun showPop(list: ArrayList<ReleasePopData>, listener: OnSelectListener) {
        releasePopData.clear()
        releasePopData.addAll(list)
        if (adapter == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.code_layout_pop_recycler, null)
            val recyclerView = view.find(R.id.rvPop) as RecyclerView
            val rl_pop = view.find(R.id.rl_pop) as RelativeLayout
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = object : BaseQuickAdapter<ReleasePopData, BaseViewHolder>(R.layout
                    .code_recycler_pop_item, releasePopData) {
                override fun convert(helper: BaseViewHolder?, item: ReleasePopData?) {
                    helper?.setText(R.id.selectAddress, item?.value)
                }
            }
            rl_pop.setOnClickListener { popSa?.dismiss() }
            recyclerView.adapter = adapter
            popSa2 = PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                    .LayoutParams
                    .MATCH_PARENT, true)
            popSa2?.isFocusable = true
            popSa2?.isOutsideTouchable = true;
            popSa2?.isTouchable = true;
            popSa2?.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
            popSa2?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } else {
            adapter?.setNewData(releasePopData)
        }
        adapter?.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                popSa?.dismiss()
            }
        }
        popSa?.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0)
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    val presenter by lazy(LazyThreadSafetyMode.NONE) { ReleaseTimePresenterImpl(this) }
    override fun onClick(v: View?) {
        presenter.onClick(v)
    }

    override fun getLayoutId(): Int {

        return R.layout.code_activity_release_parttime
    }

    override fun inits(savedInstanceState: Bundle?) {
        defaultLoaction()
        defaultMarkerDrag()
        defaultMapClick()
        putScrollView(svCreate)
        actionbar.setOnRightImageListener(View.OnClickListener {
            finish()
        })
        actionbar.setOnRightTextListener(View.OnClickListener { presenter.saveRelease() })
    }

    override fun onDestroy() {
        popSa2?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        super.onDestroy()
    }
}