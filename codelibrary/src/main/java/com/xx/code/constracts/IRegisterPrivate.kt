package com.xx.code.constracts

import android.net.Uri
import com.xx.code.responsebody.LocationBean
import com.xx.common.base.*
import com.xx.common.utils.IAMapLocationSuccessListener

/**
 * @author : zhijun.li on 2018/6/28
 *   email :
 *
 */
interface IRegisterPrivateViewModel : IBaseViewModel {
    fun addOnUpPicListener(listener: IDataFileListener)
    fun addOnUpHrListener(listener: IDataListener)
    fun addOnUpCompanyListener(listener: IDataListener)
}

interface IRegisterPrivatePresent : IBasePresenter

interface IRegisterPrivateView : IBaseViews {
    fun headImage() : Uri?
    fun selectImage()
    fun location(listener: IAMapLocationSuccessListener)
    fun getName() : String?
    fun getLocationBean() : LocationBean?
    fun upData()

}