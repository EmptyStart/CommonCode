package com.tima.common.https

import com.alibaba.android.arouter.launcher.ARouter
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.GsonUtils
import com.tima.common.utils.SpHelper
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/20
 *   email : zhijun.li@timanetworks.com
 *
 */
object ExceptionDeal {
    fun handleException(error: String) {
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let {
            try {
                val exception = GsonUtils.getGson.fromJson(error, ApiException::class.java)
                val detail = exception?.detail.toString();
                val code = exception?.code.toString()
                it.toast(detail?:"未知错误")
                if ("token失效，请重新登陆！"==exception?.detail){
                    SpHelper.clearPreference()
                    ARouter.getInstance().build(RoutePaths.login).navigation()
                    ActivityManage.instance.exitExcept("LoginActivity")
                }else{}
            }catch (e:Exception){
                e.printStackTrace()
                it.toast("未知错误")
            }
        }
    }
}
