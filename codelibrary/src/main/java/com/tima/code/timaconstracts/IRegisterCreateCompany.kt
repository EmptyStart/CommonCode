package com.tima.code.timaconstracts

import android.net.Uri
import android.widget.TextView
import com.tima.common.base.*

/**
 * @author : lzj on 2018/9/9 5:12
 *         # Email
 */

interface IRegisterCreateCompanyPresent : IBasePresenter {
    var isChangeInfo: Boolean
    fun setStaffes(key: String?)
}

interface IRegisterCreateCompanyView : IRegisterCompanyView {
    fun image21(): Uri?
    fun image22(): Uri?
    fun image23(): Uri?
    fun image3(): Uri?

    fun getStaffes(): TextView

    fun getIntroduce(): String?
    fun getWebAddress(): String?
}