package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.tima.code.R
import com.tima.code.responsebody.*
import com.tima.code.timaconstracts.IReleaseTimePresent
import com.tima.code.timaconstracts.IReleaseTimeView
import com.tima.code.timaconstracts.OnSelectListener
import com.tima.code.timaviewmodels.ReleaseTimeViewModelImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.IDataListener
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.DateUtils
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.KeyboardUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import org.jetbrains.anko.find
import java.util.*

/**
 * @author : zhijun.li on 2018/9/12
 *   email : zhijun.li@timanetworks.com
 *
 */
class ReleaseTimePresenterImpl(view: IReleaseTimeView) : IReleaseTimePresent {

    var mView: IReleaseTimeView? = view
    val popData = arrayListOf<ReleasePopData>()
    //职位类型
    val careerTypes = arrayListOf<CareerTypeResult>()
    //选中的职位类型
    var selectCareerData: String? = null

    //标签类型（职位类型下的子表）  （有就有值 无就为空）
    val tagList = arrayListOf<String>()
    val skillSet: String? = null
    //全职工做时间选择弹窗
    var popFullDateWindow: PopupWindow? = null
    //工作经验
    val workExpResults = arrayListOf<WorkExpResult>()
    //工作经验选中
    var workExpResult: WorkExpResult? = null

    //薪资范围
    val workSalaryResults = arrayListOf<WorkExpResult>()
    //薪资范围选中
    var workSalaryResult: WorkExpResult? = null

    //兼职时间
    var partTimeSelect: String = ""
    private val mViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ReleaseTimeViewModelImpl()
    }
    //星期选择的结果
    private var selectWeeksData = ""
    //小时选择的结果
    private var selectTimesData = ""
    //薪水单位
    private var salaryUnit = ""
    //最低学历
    private var edu = ""

    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {
                R.id.iv_actionbar_cancle -> {
                    (view.context as Activity).finish()
                }

                R.id.rl_work_type -> {
                    KeyboardUtils.hideInput(it.context as Activity)
                    careerType(0)
                }
                R.id.ll_cycle -> {
                    //周期选择
                    KeyboardUtils.hideInput(it.context as Activity)
                    otherPicker(2, null)
                }
                R.id.ll_time -> {
                    //时间选择
                    KeyboardUtils.hideInput(it.context as Activity)
                    timePickerSelect()
                }
                R.id.ll_educat -> {
                    //学历选择
                    KeyboardUtils.hideInput(it.context as Activity)
                    otherPicker(0, null)
                }
                R.id.ll_work -> {
                    //工作经验选择
                    KeyboardUtils.hideInput(it.context as Activity)
                    pickWorkExp(0)
                }
                R.id.tv_add -> {
                    //增加人数
                    KeyboardUtils.hideInput(it.context as Activity)
                    try {
                        var qty = 0
                        mView?.getQty()?.toInt()?.let {
                            qty = it
                            qty++
                            mView?.setQty(qty)
                        }

                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }
                }
                R.id.tv_minus -> {
                    //减少人数
                    KeyboardUtils.hideInput(it.context as Activity)
                    try {
                        var qty = 0
                        mView?.getQty()?.toInt()?.let {
                            if (it == 0) return
                            qty = it
                            qty--
                            mView?.setQty(qty)
                        }

                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }
                }
                R.id.ll_wages -> {
                    //薪资范围
                    KeyboardUtils.hideInput(it.context as Activity)
                    pickWorkExp(1)
                }
                R.id.ll_interview_date -> {
                    KeyboardUtils.hideInput(it.context as Activity)
                    popDataSelect(0)
                }
                R.id.ll_interview_time -> {
                    KeyboardUtils.hideInput(it.context as Activity)
                    popDataSelect(1)
                }

                R.id.tv_introduce -> {
                    KeyboardUtils.hideInput(it.context as Activity)
                    mView?.selectTag(tagList)
                }
                else -> {

                }
            }
        }
    }

    //0/全职 1/兼职
    override fun saveRelease(code: Int) {
        mView?.apply {
            val pairs = mutableMapOf<String, String>()
            pairs["type"] = code.toString()
            val workName = getWorkName()
            if (workName.isNullOrEmpty()) {
                showError("职位名称是必填项")
                return
            } else {
                pairs["name"] = workName!!
            }
            if (selectCareerData.isNullOrEmpty()) {

            } else {
                pairs["career_type"] = selectCareerData!!
            }


            val locationBean = getLocationBean()
            if (locationBean == null) {
                showError("详细地址是必填项")
                return
            } else {
                pairs["address"] = locationBean.snippet
                pairs["province"] = locationBean.province
                pairs["city"] = locationBean.city
                pairs["region"] = locationBean.district
                pairs["latitude"] = locationBean.latitude.toString()
                pairs["longitude"] = locationBean.longitude.toString()
            }
            val wordDec = getWordDec()
            if (!wordDec.isNullOrEmpty()) {
                pairs["descriptions"] = wordDec!!
            }
            workExpResult?.apply {
                val split = key.split("-")
                if (split.size > 1) {
                    pairs["exp_year_beg"] = split[0]
                    pairs["exp_year_end"] = split[1]
                }
            }
            if (!skillSet.isNullOrEmpty()) {
                pairs["skill_set"] = skillSet!!
            }
            if (code == 1) {
                pairs["salary_begin"] = "10"
                pairs["salary_end"] = "10"
                if (partTimeSelect.isEmpty()) {
                    showError("请选择到场时间")
                    return
                } else {
                    pairs["on_site_time"] = partTimeSelect
                }
                val wagePart = getWage()
                if (wagePart.isNullOrEmpty()) {
                    showError("请输入单位薪水")
                    return
                } else {
                    pairs["salary_qty"] = wagePart!!
                }
                val qty = getQty()
                if (qty.isNullOrEmpty()) {
                    showError("请输入需求人数")
                } else {
                    pairs["qty"] = qty!!
                    pairs["qty_var"] = qty!!
                }
            }

            if (code == 0) {
                if (workSalaryResult != null) {
                    workSalaryResult?.apply {
                        val split = key.split("-")
                        if (split.size > 1) {
                            pairs["salary_begin"] = split[0]
                            pairs["salary_end"] = split[1]
                        }
                    }
                } else {
                    showError("请选择薪资范围！")
                    return
                }
                if (selectWeeksData.isEmpty() || selectTimesData.isEmpty()) {
                    showError("请选择面试日期！")
                    return
                } else {
                    pairs["interview_day"] = selectWeeksData
                    pairs["interview_time"] = selectTimesData
                }
            }


            if (salaryUnit.isNotEmpty()) {
                pairs["salary_unit"] = salaryUnit
            }
            if (edu.isNotEmpty()) {
                pairs["education"] = edu
            }
            upRelease(pairs)
        }
    }

    fun upRelease(requestData: Map<String, String>) {
        mView?.showLoading()
        mViewModel.addOnReleaseTimeListener(object : IDataListener {
            override fun requestData(): Map<String, String>? {
                return requestData
            }

            override fun successData(success: String) {
                mView?.hideLoading()
                mView?.showError("发布成功")
                val currentActivity = ActivityManage.instance.getCurrentActivity()
                currentActivity?.finish()
            }

            override fun errorData(error: String) {
                mView?.hideLoading()
                ExceptionDeal.handleException(error)
            }
        })
    }

    private fun careerType(code: Int) {
        Observable.create(ObservableOnSubscribe<ArrayList<CareerTypeResult>> { emitter ->
            if (careerTypes.isEmpty()) {
                mView?.showLoading()
                mViewModel.addCareertype(object : IDataListener {
                    override fun requestData(): Map<String, String>? {
                        return null
                    }

                    override fun successData(success: String) {
                        val careerTypeBody = GsonUtils.getGson.fromJson(success, CareerTypeBody::class.java)
                        val results = careerTypeBody?.results
                        careerTypes.clear()
                        results?.let {
                            careerTypes.addAll(it)
                            emitter.onNext(careerTypes)
                        }
                    }

                    override fun errorData(error: String) {
                        mView?.hideLoading()
                        ExceptionDeal.handleException(error)
                    }
                })
            } else {
                emitter.onNext(careerTypes)
            }
        }).subscribe {
            mView?.hideLoading()
            popData.clear()
            it.forEach {
                if (it.parent == code) {
                    popData.add(ReleasePopData(0, "", it.name, it.id, it.parent, it.skills))
                }
            }
            careerDataDeal()
        }


    }

    private fun careerDataDeal() {
        if (popData.isNotEmpty()) {
            mView?.showPop(popData, object : OnSelectListener {
                override fun selected(result: ReleasePopData) {
                    popData.clear()
                    careerTypes.forEach {
                        if (it.parent == result.id) {
                            popData.add(ReleasePopData(0, "", it.name, it.id, it.parent, it.skills))
                        }
                    }
                    if (popData.isNotEmpty()) {
                        mView?.showPop(popData, object : OnSelectListener {
                            override fun selected(result: ReleasePopData) {
                                selectCareerData = result.id.toString()
                                mView?.setWorkType(result.value)
                                tagList.clear()
                                result.skills?.forEach {
                                    tagList.add(it.toString())
                                }
                            }
                        })
                    } else {
                        selectCareerData = result.id.toString()
                        mView?.setWorkType(result.value)
                        result.skills?.forEach {
                            tagList.add(it.toString())
                        }
                    }
                }
            })
        }
    }

    //全职的时间选择 0/星期  1/时间
    private fun popDataSelect(code: Int) {
        val weeks = arrayListOf("周一", "周二", "周三", "周四", "周五", "周六", "周七")
        val dates = arrayListOf("8:00-9:00", "9:00-10:00", "10:00-11:00", "11:00-12:00"
                , "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00",
                "17:00-18:00", "18:00-19:00", "19:00-20:00", "20:00-21:00", "21:00-22:00")
        val popDatas = arrayListOf<String>()
        when (code) {
            0 -> popDatas.addAll(weeks)
            1 -> popDatas.addAll(dates)
        }
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { activity ->
            val view = LayoutInflater.from(activity).inflate(R.layout.code_layout_pop_flowlayout, null)
            val popTvCancel = view.find(R.id.popTvCancel) as TextView
            val popTvSure = view.find(R.id.popTvSure) as TextView
            val popTvTitle = view.find(R.id.popTvTitle) as TextView
            val popTfl = view.find(R.id.popTfl) as TagFlowLayout
            popFullDateWindow = PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                    .MATCH_PARENT, true)

            val clickListener = View.OnClickListener { v ->
                when (v?.id) {
                    R.id.popTvCancel -> {
                        popFullDateWindow?.dismiss()
                    }
                    R.id.popTvSure -> {
                        val sb = StringBuilder()
                        popTfl.selectedList.forEach {
                            if (code == 0) {
                                sb.append(it)
                            } else {
                                sb.append(it + 8)
                            }
                            sb.append(",")
                        }
                        val length = sb.length
                        if (length > 1) {
                            val toString = sb.substring(0, length - 1).toString()
                            if (code == 0) {
                                selectWeeksData = toString
                                mView?.setInWeek(1)
                            } else {
                                selectTimesData = toString
                                mView?.setInTime(1)
                            }
                        } else {
                            if (code == 0) {
                                mView?.setInWeek(0)
                            } else {
                                mView?.setInTime(0)
                            }
                        }
                        popFullDateWindow?.dismiss()
                    }
                }
            }
            popTvCancel.setOnClickListener(clickListener)
            popTvSure.setOnClickListener(clickListener)
            popTfl.adapter = object : TagAdapter<String>(popDatas) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val textView = LayoutInflater.from(activity).inflate(R.layout.code_flowlayout_item, parent, false)
                            as TextView
                    textView.text = t
                    return textView
                }
            }
            popFullDateWindow?.isFocusable = true
            popFullDateWindow?.isOutsideTouchable = true;
            popFullDateWindow?.isTouchable = true;
            popFullDateWindow?.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
            popFullDateWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            popFullDateWindow?.showAtLocation((activity as BaseActivity).getRootView(), Gravity
                    .BOTTOM, 0, 0)
        }
    }

    //兼职时间选择
    private fun timePickerSelect() {
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { activity ->
            val pickerView = TimePickerBuilder(activity, object : OnTimeSelectListener {
                override fun onTimeSelect(date: Date?, v: View?) {
                    date?.let {
                        val s = DateUtils.getDateYMDHMS_(it)
                        mView?.setPartMs(s)
                        partTimeSelect = s
                    }
                }

            })
                    .isCyclic(true)
                    .setType(booleanArrayOf(true, true, true, true, true, false))
                    .build()

            pickerView.show()
        }
    }

    //0/学历选择 1/工作经验选择 2/周期选择 3/薪资范围
    private fun otherPicker(code: Int, its: ArrayList<String>?) {
        var text = "最低学历"
        var items = R.array.edus
        if (code == 1) {
            text = "工作经验"
        }
        if (code == 2) {
            text = "周期选择"
            items = R.array.cycle_time
        }

        if (code == 3) {
            text = "薪资范围"
        }

        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { activity ->
            val onPositive = MaterialDialog.Builder(activity)
                    .title(text)
                    .canceledOnTouchOutside(true)
                    .positiveText("确定")
                    .itemsCallbackSingleChoice(0) { dialog, itemView, which, text -> true }
                    .onPositive(object : MaterialDialog.SingleButtonCallback {
                        override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                            dialog.dismiss()
                            val selectedIndex = dialog.selectedIndex
                            mView?.apply {
                                when (code) {
                                    0 -> {
                                        edu = selectedIndex.toString()
                                        setEdu(activity.resources.getStringArray(items).get(selectedIndex))
                                    }

                                    1 -> {
                                        setExp(its?.get(selectedIndex))
                                        workExpResult = workExpResults.get(selectedIndex)
                                    }

                                    2 -> {
                                        setSalaryUnit(activity.resources.getStringArray(items).get(selectedIndex))
                                        salaryUnit = selectedIndex.toString()
                                    }
                                    3 -> {
                                        setWage(its?.get(selectedIndex))
                                        workSalaryResult = workSalaryResults.get(selectedIndex)
                                    }
                                }
                            }

                        }
                    })
            if (code != 1 && code != 3) {
                onPositive.items(items)
            } else {
                onPositive.items(its!!)
            }
            onPositive.show()
        }
    }

    private fun pickWorkExp(code: Int) {
        Observable.create(ObservableOnSubscribe<ArrayList<WorkExpResult>> { emitter ->
            if (code == 0 && workExpResults.isNotEmpty()) {
                emitter.onNext(workExpResults)
            } else if (code == 1 && workSalaryResults.isNotEmpty()) {
                emitter.onNext(workSalaryResults)
            } else {
                mView?.showLoading()
                mViewModel.addConfigInfo(object : IDataListener {
                    override fun requestData(): Map<String, String>? {
                        if (code == 1) {
                            return mapOf(Pair("type", "EXPECTED_SALARY "))
                        }
                        return mapOf(Pair("type", "WORK_EXPERIENCE"))
                    }

                    override fun successData(success: String) {
                        mView?.hideLoading()
                        val workExpResponse = GsonUtils.getGson.fromJson(success, WorkExpResponse::class.java)
                        val results = workExpResponse.results
                        if (code == 1) {
                            workSalaryResults.clear()
                            workSalaryResults.addAll(results)
                            emitter.onNext(workSalaryResults)
                        } else {
                            workExpResults.clear()
                            workExpResults.addAll(results)
                            emitter.onNext(workExpResults)
                        }
                    }
                    override fun errorData(error: String) {
                        mView?.hideLoading()
                        ExceptionDeal.handleException(error)
                    }
                })
            }
        }).subscribe {
            val exp = arrayListOf<String>()
            it?.forEach { workExpResult ->
                exp.add(workExpResult.value)
            }
            if (code == 1) {
                otherPicker(3, exp)
            } else {
                otherPicker(1, exp)
            }
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewModel.detachView()
        mView == null
        popFullDateWindow?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

}