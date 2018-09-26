package com.tima.code.timaconstracts

import android.net.Uri
import com.tima.code.responsebody.LocationBean
import com.tima.common.base.*
import com.tima.common.utils.IAMapLocationSuccessListener

/**
 * @author : lzj on 2018/9/9 5:12
 *         # Email
 */
interface IRegisterCompanyViewModel : IBaseViewModel {
    fun addOnUpPicListener(listener: IDataFileListener)
    fun addOnUpHrListener(listener: IDataListener)
    fun addOnUpCompanyListener(listener: IDataListener)
}

interface IRegisterCompanyPresent : IBasePresenter

interface IRegisterCompanyView : IBaseViews {
    fun headImage() : Uri?
    fun selectImage(code : Int)
    fun location(listener: IAMapLocationSuccessListener)
    fun getName() : String?
    fun getLocationBean() : LocationBean?
}