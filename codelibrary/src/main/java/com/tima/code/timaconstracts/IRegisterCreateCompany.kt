package com.tima.code.timaconstracts

import android.net.Uri
import android.widget.TextView
import com.tima.code.ResponseBody.LocationBean
import com.tima.common.base.*
import com.tima.common.utils.IAMapLocationSuccessListener

/**
 * @author : lzj on 2018/9/9 5:12
 *         # Email
 */

interface IRegisterCreateCompanyPresent : IBasePresenter

interface IRegisterCreateCompanyView : IRegisterCompanyView {
    fun image21() : Uri?
    fun image22() : Uri?
    fun image23() : Uri?
    fun imageLogo() : Uri?

    fun getStaffes() : TextView
}