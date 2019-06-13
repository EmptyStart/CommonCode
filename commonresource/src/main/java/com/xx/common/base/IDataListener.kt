package com.xx.common.base

import okhttp3.MultipartBody

/**
 * @author : zhijun.li on 2018/6/28
 *   email :
 *
 */

interface IDataListener : IBaseDataListener{

    fun requestData() : Map<String,String>?
}
interface IDataFileListener : IBaseDataListener{
    fun requestFileData() : MultipartBody.Part
    fun requestType() : MultipartBody.Part?
    fun requestExt() : MultipartBody.Part?
}
interface IBaseDataListener{
    fun successData(success : String)
    fun errorData(error : String)
}