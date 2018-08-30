package com.tima.code.timaconstracts

import android.app.Activity
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews

/**
 * Created by Administrator on 2018/8/30/030.
 */
interface IMineViewModel : IBaseViewModel

interface IMineView : IBaseViews {
    fun getMineActivity() : Activity
}
interface IMinePresent : IBasePresenter
