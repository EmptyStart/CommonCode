package com.tima.common.utils

import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.RequiresApi
import com.tima.common.base.App

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
object ColorIdUtil {

    fun getColorId(colorId: Int): Int {

        if (Build.VERSION.SDK_INT >= 23) {
            return App.app.resources.getColor(colorId, null)
        } else {
            return App.app.resources.getColor(colorId);
        }
    }
}