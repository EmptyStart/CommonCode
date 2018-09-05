package com.tima.common.base

import okhttp3.MultipartBody
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */

interface IDataListener{
    fun successData(success : String)
    fun errorData(error : String)
    fun requestData() : Map<String,String>?
}
interface IDataFileListener : IDataListener{
    fun requestFileData() : MultipartBody.Part
}