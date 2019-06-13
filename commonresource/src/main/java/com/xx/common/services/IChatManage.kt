package com.xx.common.services

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author : zhijun.li on 2018/9/19
 *   email :
 *
 */
interface IChatManage :IProvider{
    fun initChat(): Boolean
    fun login(name : String,password : String) : Boolean
    fun loginOut()  : Boolean
}