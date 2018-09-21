package com.tima.code.timaconstracts

import android.app.Activity
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews

/**
 * Created by Administrator on 2018/9/21.
 */
interface IPaymentMethodModel : IBaseViewModel

interface IPaymentMethodView : IBaseViews {
    fun getPaymentMethodActivity() : Activity
}
interface IPaymentMethodPresent : IBasePresenter