package com.tima.code.timaconstracts

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
}

interface IReleaseTimeView : IBaseViews {
    fun showPop(list: ArrayList<ReleasePopData>, listener: OnSelectListener)
}

interface OnSelectListener {
    fun selected(result: ReleasePopData)
}
