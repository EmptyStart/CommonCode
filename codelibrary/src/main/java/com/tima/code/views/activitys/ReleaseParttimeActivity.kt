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
import com.tima.code.ResponseBody.LocationBean
import com.tima.code.ResponseBody.ReleasePopData
import com.tima.code.timaconstracts.IReleaseTimeView
import com.tima.code.timaconstracts.OnSelectListener
import com.tima.code.timapresenter.ReleaseTimePresenterImpl
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_release_parttime.*
import kotlinx.android.synthetic.main.code_layout_select_address.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/29
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.releaseparttime)
class ReleaseParttimeActivity : AbstractAddressAndMapActivity(), View.OnClickListener, IReleaseTimeView {
    override fun getWage(): String? {
        return et_wage.text.toString().trim()
    }

    override fun getWordDec(): String? {
        return et_work_desc.text.toString().trim()
    }

    override fun getSalaryBegin(): String? {
        return null
    }

    override fun recycleTimes(): String? {
        return tv_cycle.text.toString().trim()
    }

    override fun salaryUnit(): String? {
        return et_cycle.text.toString().trim()
    }

    override fun getWorkName(): String? {
        return et_work.text.toString().trim()
    }

    override fun getLocationBean(): LocationBean? {
        val pro = resources.getString(R.string.code_register_pro)
        val citys = resources.getString(R.string.code_register_city)
        val county = resources.getString(R.string.code_register_county)
        val tvPro = tv_pro.text.toString()
        val tvCity = tv_city.text.toString()
        val tvCounty = tv_county.text.toString()
        val etAddress = et_address.text.toString()

        if (pro.equals(tvPro)||citys.equals(tvCity)||county.equals(tvCounty)||etAddress.isEmpty()){
            return null
        }
        if (selectPosition!=-1){
            return locations.get(selectPosition)
        }
        val aMapLocation = Constant.aMapLocation
        aMapLocation?.apply {
            return LocationBean(latitude,longitude,"",tvPro,tvCity,tvCounty,etAddress)
        }
        return null
    }

    override fun getQty(): String? {
        return et_count.text.toString().trim()
    }

    override fun getSkillSet(): String? {
        return null
    }

    override fun setWorkType(name: String?) {
        name?.let{
            tvWorkTyp.text=name
        }
    }

    private var releaseTimeAdapter: BaseQuickAdapter<ReleasePopData, BaseViewHolder>? = null
    private var popSelect: PopupWindow? = null
    val releasePopData = arrayListOf<ReleasePopData>()
    override fun showPop(list: ArrayList<ReleasePopData>, listener: OnSelectListener) {
        releasePopData.clear()
        releasePopData.addAll(list)
        if (releaseTimeAdapter == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.code_layout_pop_recycler, null)
            val recyclerView = view.find(R.id.rvPop) as RecyclerView
            val rl_pop = view.find(R.id.rl_pop) as RelativeLayout
            recyclerView.layoutManager = LinearLayoutManager(this)
            releaseTimeAdapter = object : BaseQuickAdapter<ReleasePopData, BaseViewHolder>(R.layout
                    .code_recycler_pop_item, releasePopData) {
                override fun convert(helper: BaseViewHolder?, item: ReleasePopData?) {
                    helper?.setText(R.id.selectAddress, item?.value)
                }
            }
            rl_pop.setOnClickListener { popSelect?.dismiss() }
            recyclerView.adapter = releaseTimeAdapter
            popSelect = PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                    .LayoutParams
                    .MATCH_PARENT, true)
            popSelect?.isFocusable = true
            popSelect?.isOutsideTouchable = true;
            popSelect?.isTouchable = true;
            popSelect?.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
            popSelect?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            releaseTimeAdapter?.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
                override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                    popSelect?.dismiss()
                }
            }
        } else {
            releaseTimeAdapter?.setNewData(releasePopData)
        }
        popSelect?.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0)
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
        popSelect?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        super.onDestroy()
    }
}