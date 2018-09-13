package com.tima.code.timaconstracts

import com.tima.code.ResponseBody.LocationBean
import com.tima.code.ResponseBody.ReleasePopData
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener

/**
 * @author : zhijun.li on 2018/9/12
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IReleaseTimeViewModel : IBaseViewModel {
    fun addOnReleaseTimeListener(listener: IDataListener)
}

interface IReleaseTimePresent : IBasePresenter {
    fun saveRelease()
    //选择单位
    fun selectRecTime()
    //选择学历
    fun selectEdu()
}

interface IReleaseTimeView : IBaseViews {
    fun showPop(list: ArrayList<ReleasePopData>, listener: OnSelectListener)
    fun setWorkType(name : String?)
    fun getWorkName() : String?
    fun getLocationBean() : LocationBean?
    //需求人数
    fun getQty(): String?
    //兼职周期
    fun recycleTimes() :String?
    //薪资单位
    fun salaryUnit() :String?
    //技能
    fun getSkillSet() : String?
    //薪资
    fun getWage() : String?
    //职位描述
    fun getWordDec() : String?
    //起薪
    fun getSalaryBegin() :String?


}

interface OnSelectListener {
    fun selected(result: ReleasePopData)
}
