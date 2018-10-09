package com.tima.code.timaconstracts

import android.app.Activity
import com.tima.common.base.*

/**
 * Created by Administrator on 2018/8/30/030.
 */
interface IMineViewModel : IBaseViewModel{
    fun logout(listener: IDataListener)
}

interface IMineView : IBaseViews {
    fun getMineActivity() : Activity
}
interface IMinePresent : IBasePresenter