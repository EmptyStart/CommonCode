package com.tima.common.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Administrator on 2018/8/21/021.
 */
object DateUtils{
    /**
     * 将字符串转为时间戳
     * 格式 yyyy-MM-dd HH:mm:ss
     */
    fun getLongYMDHMS(date: String): Long {
        var ltime: Long = 0
        if (TextUtils.isEmpty(date)) {
            ltime = System.currentTimeMillis()
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            try {
                val d = sdf.parse(date)
                ltime = d.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
        return ltime
    }

    fun getDateYMDHMS(mill: Long): String {
        val date = Date(mill)
        var strs = ""
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            strs = sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strs
    }

}