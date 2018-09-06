package com.tima.common.https

import android.widget.Toast
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.GsonUtils
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
            val exception = GsonUtils.getGson.fromJson(error, ApiException::class.java)
            val detail = exception?.detail.toString();
            val code = exception?.code.toString()
            it.toast(detail?:"未知错误")
        }
    }
}
