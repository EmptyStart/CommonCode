package com.xx.code.constracts

import android.app.Activity
import com.xx.common.base.IBasePresenter
import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IBaseViews

/**
 * Created by Administrator on 2018/9/21.
 */
interface IPaymentMethodModel : IBaseViewModel

interface IPaymentMethodView : IBaseViews {
    fun getPaymentMethodActivity() : Activity
}
interface IPaymentMethodPresent : IBasePresenter