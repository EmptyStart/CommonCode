package com.tima.common.utils

import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.tima.common.base.App

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
object ResourceUtil {

    fun getColorId(colorId: Int): Int {

        if (Build.VERSION.SDK_INT >= 23) {
            return App.app.resources.getColor(colorId, null)
        } else {
            return App.app.resources.getColor(colorId);
        }
    }

    fun getStringArray(arrayId: Int): Array<String> {
        return App.app.resources.getStringArray(arrayId)
    }

    fun getBitmapDescriptorFactory(imageIdRes: Int): BitmapDescriptor? {
        try {
            val bitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(App.app.resources, imageIdRes))
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}