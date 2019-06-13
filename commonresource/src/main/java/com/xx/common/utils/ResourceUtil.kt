package com.xx.common.utils

import android.graphics.BitmapFactory
import android.os.Build
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.xx.common.base.App

/**
 * @author : zhijun.li on 2018/8/23
 *   email :
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

    fun getStringArrayValue(arrayId: Int?,position: Int?): String {
        var result = ""
        if (arrayId != null && position != null) {
            var arrays = App.app.resources.getStringArray(arrayId!!)
            if (arrays.size > position!!) {
                result = arrays[position-1]
            }
        }
        return result
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