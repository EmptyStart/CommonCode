package com.tima.code.timaconstracts

/**
 * @author : zhijun.li on 2018/6/28
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IDataListener{
    fun successData(success : String?)
    fun requestData() : Map<String,String>?
}