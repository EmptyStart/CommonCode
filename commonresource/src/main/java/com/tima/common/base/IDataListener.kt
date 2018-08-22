package com.tima.common.base

import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IDataListener{
    fun successData(success : ResponseBody)
    fun errorData(error : String)
    fun requestData() : Map<String,String>?
}