package com.tima.common.https

import android.widget.Toast
import com.tima.common.utils.ActivityManage
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/20
 *   email : zhijun.li@timanetworks.com
 *
 */
object ExceptionDeal {
    fun handleException(exception: ApiException) {
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity.let {
            Toast.makeText(it, exception.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
