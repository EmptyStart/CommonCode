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
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.responsebody.LocationBean
import com.tima.code.responsebody.ReleasePopData
import com.tima.code.timaconstracts.IReleaseTimeView
import com.tima.code.timaconstracts.OnSelectListener
import com.tima.code.timapresenter.ReleaseTimePresenterImpl
import com.tima.common.BusEvents.CanSelectTag
import com.tima.common.BusEvents.SelectedTag
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_release_fulltime.*
import kotlinx.android.synthetic.main.code_layout_select_address.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.lang.StringBuilder

/**
 * @author : zhijun.li on 2018/8/29
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.releasefulltime)
class ReleaseFulltimeActivity : AbstractAddressAndMapActivity() , IReleaseTimeView ,View.OnClickListener{
    override fun setInWeek(code: Int) {
        if (code==0){
            tv_interview_week.setText("请选择")
            return
        }
        tv_interview_week.setText("已选择")
    }

    override fun setInTime(code: Int) {
        if (code==0){
            tv_interview_time.setText("请选择")
            return
        }
        tv_interview_time.setText("已选择")
    }

    override fun selectTag(array: ArrayList<String>) {
        if (array.size>0) {
            val canSelectTag = CanSelectTag()
            canSelectTag.tagList.addAll(array)
            EventBus.getDefault().postSticky(canSelectTag)
        }
        ARouter.getInstance().build(RoutePaths.releasetag).navigation()
    }

    val tagData= arrayListOf<String>()
    override fun onClick(v: View?) {
        presenter.onClick(v)
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
                    listener.selected(releasePopData.get(position))
                }
            }
        } else {
            releaseTimeAdapter?.setNewData(releasePopData)
        }
        popSelect?.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0)
    }

    override fun setWorkType(name: String?) {
        name?.let{
            tvWorkTyp.text=name
        }
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
        return null
    }

    override fun setQty(value: Any?) {
    }

    override fun cycleTimes(): String? {
        return null
    }

    override fun setCycleTimes(value: Any?) {
    }

    override fun salaryUnit(): String? {
        return null
    }

    override fun setSalaryUnit(salary: String?) {
    }

    override fun getSkillSet(): String? {
        return null
    }

    override fun getWage(): String? {
        return null
    }

    override fun setWage(exp: String?) {
        exp?.let {
            tv_wages.text=it
        }
    }

    override fun getWordDec(): String? {
        return et_work_desc.text.toString().trim()
    }


    override fun setEdu(edu: String?) {
        edu?.let {
            tv_educat.text=it
        }
    }

    override fun setExp(exp: String?) {
        exp?.let {
            tv_work.text=it
        }
    }

    override fun setPartMs(exp: String?) {
    }

    override fun getPartMs(): String? {
        return "0"
    }

    override fun showLoading() {
        loadingBar.show()
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    val presenter by lazy(LazyThreadSafetyMode.NONE) { ReleaseTimePresenterImpl(this) }
    override fun getLayoutId(): Int {

        return R.layout.code_activity_release_fulltime
    }

    override fun inits(savedInstanceState: Bundle?) {
        defaultLoaction()
        defaultMarkerDrag()
        defaultMapClick()
        putScrollView(svCreate)
        actionbar.setOnRightImageListener(this)
        actionbar.setOnRightTextListener(View.OnClickListener {
            presenter.saveRelease(0)
        })
        rl_work_type.setOnClickListener(this)
        ll_educat.setOnClickListener(this)
        ll_work.setOnClickListener(this)
        ll_wages.setOnClickListener(this)
        ll_interview_date.setOnClickListener(this)
        ll_interview_time.setOnClickListener(this)
        tv_introduce.setOnClickListener(this)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getTagList(array : SelectedTag){
        tagData.clear()
        tagData.addAll(array.tagList)
        val sb = StringBuilder()
        tagData.forEach {
            sb.append(it)
            sb.append(";")
        }
        tv_introduce.setText(sb.subSequence(0,sb.length-1))
    }
    override fun useEventBus(): Boolean {
        return true
    }
}