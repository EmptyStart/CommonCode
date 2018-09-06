package com.tima.code.timaconstracts

import android.database.Observable
import android.net.Uri
import com.tima.common.base.*
import com.tima.common.utils.IAMapLocationSuccessListener
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IRegisterPrivateViewModel : IBaseViewModel {
    fun addOnUpPicListener(listener: IDataFileListener)
    fun addOnUpHrListener(listener: IDataListener)
//    fun addOnUpHrListener(listener: IDataListener)
}

interface IRegisterPrivatePresent : IBasePresenter

interface IRegisterPrivateView : IBaseViews {
    fun headImage() : Uri?
    fun selectImage()
    fun location(listener: IAMapLocationSuccessListener)
}