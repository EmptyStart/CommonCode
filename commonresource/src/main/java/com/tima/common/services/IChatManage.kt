package com.tima.common.services

import android.app.Activity
import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author : zhijun.li on 2018/9/19
 *   email : zhijun.li@timanetworks.com
 *
 */
interface IChatManage :IProvider{
    fun initChat(): Boolean
    fun login(name : String,password : String, activity : Activity) : Boolean
    fun loginOut(activity: Activity)  : Boolean
    fun toast()
}