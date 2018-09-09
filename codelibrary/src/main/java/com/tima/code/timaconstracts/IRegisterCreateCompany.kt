package com.tima.code.timaconstracts

import android.net.Uri
import com.tima.code.ResponseBody.LocationBean
import com.tima.common.base.*
import com.tima.common.utils.IAMapLocationSuccessListener

/**
 * @author : lzj on 2018/9/9 5:12
 *         # Email
 */
interface IRegisterCreateCompanyViewModel : IBaseViewModel {
    fun addOnUpPicListener(listener: IDataFileListener)
    fun addOnUpHrListener(listener: IDataListener)
    fun addOnUpCompanyListener(listener: IDataListener)
}

interface IRegisterCreateCompanyPresent : IBasePresenter

interface IRegisterCreateCompanyView : IRegisterCompanyView {
    fun image21() : Uri?
    fun image22() : Uri?
    fun image23() : Uri?
}