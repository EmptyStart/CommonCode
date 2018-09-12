package com.tima.code.timaconstracts

import com.tima.code.ResponseBody.CareerTypeResult
import com.tima.common.base.*

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
}

interface IReleaseTimeView : IBaseViews {
    fun showPop(list: ArrayList<CareerTypeResult>, listener: OnSelectListener)
}

interface OnSelectListener {
    fun selected(result: CareerTypeResult)
}
