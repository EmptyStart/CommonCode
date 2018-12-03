package com.tima.code.timaconstracts

import android.net.Uri
import com.tima.code.responsebody.LocationBean
import com.tima.common.base.*
import com.tima.common.utils.IAMapLocationSuccessListener

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