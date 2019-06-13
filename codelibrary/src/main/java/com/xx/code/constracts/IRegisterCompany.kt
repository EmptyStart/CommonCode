package com.xx.code.constracts

import android.net.Uri
import com.xx.code.responsebody.LocationBean
import com.xx.common.base.*
import com.xx.common.utils.IAMapLocationSuccessListener

/**
 * @author : lzj on 2018/9/9 5:12
 *         # Email
 */
interface IRegisterCompanyViewModel : IBaseViewModel {
    fun addOnUpPicListener(listener: IDataFileListener)
    fun addOnUpHrListener(listener: IDataListener)
    fun addOnUpCompanyListener(listener: IDataListener)
//    fun addOnCompany
}

interface IRegisterCompanyPresent : IBasePresenter

interface IRegisterCompanyView : IBaseViews {
    fun headImage() : Uri?
    fun selectImage(code : Int)
    fun location(listener: IAMapLocationSuccessListener)
    fun getName() : String?
    fun getLocationBean() : LocationBean?
}