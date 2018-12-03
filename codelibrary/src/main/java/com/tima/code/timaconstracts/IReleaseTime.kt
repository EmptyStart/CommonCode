package com.tima.code.timaconstracts

import com.tima.code.responsebody.LocationBean
import com.tima.code.responsebody.ReleasePopData
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener

/**
 * @author : zhijun.li on 2018/9/12
 *   email :
 *
 */
interface IReleaseTimeViewModel : IBaseViewModel {
    fun addOnReleaseTimeListener(listener: IDataListener)
    fun addOnRepayListener(listener: IDataListener)
}

interface IReleaseTimePresent : IBasePresenter {
    fun saveRelease(code : Int)
    fun isCanRe() : Boolean
}

interface IReleaseTimeView : IBaseViews {
    fun showPop(list: ArrayList<ReleasePopData>, listener: OnSelectListener)
    fun setWorkType(name: String?)
    fun getWorkName(): String?
    fun getLocationBean(): LocationBean?
    //需求人数
    fun getQty(): String?

    fun setQty(value: Any?)
    //兼职周期
    fun cycleTimes(): String?

    fun setCycleTimes(value: Any?)
    //薪资单位
    fun salaryUnit(): String?

    fun setSalaryUnit(salary: String?)
    //技能
    fun getSkillSet(): String?

    //薪资
    fun getWage(): String?
    fun setWage(exp: String?)

    //职位描述
    fun getWordDec(): String?

    //设置最低学历
    fun setEdu(edu: String?)

    //设置工作经验
    fun setExp(exp: String?)
    //兼职到场时间
    fun setPartMs(exp: String?)
    //获取兼职到场时间
    fun getPartMs(): String?
    //选择标签
    fun selectTag(array: ArrayList<String>)
    //面试日期
    fun setInWeek(code: Int)
    //面试时间
    fun setInTime(code: Int)

    //合计金额
    fun getCountMoney() : String?

    //运行误差人数
    fun getQtyVar() : String?

    fun close()

    fun getTags() : String?
}

interface OnSelectListener {
    fun selected(result: ReleasePopData)
}
