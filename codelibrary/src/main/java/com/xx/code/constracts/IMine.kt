package com.xx.code.constracts

import android.app.Activity
import com.xx.common.base.*

/**
 * Created by Administrator on 2018/8/30/030.
 */
interface IMineViewModel : IBaseViewModel{
    fun logout(listener: IDataListener)
}

interface IMineView : IBaseViews {
    fun getMineActivity() : Activity
    fun callPhone()
    fun gotoChangeInfo()
}
interface IMinePresent : IBasePresenter