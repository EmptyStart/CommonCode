package com.tima.code.utils

import com.tima.code.R
import com.tima.common.utils.ResourceUtil

/**
 * @author : zhijun.li on 2018/10/12
 *   email : zhijun.li@timanetworks.com
 *
 */
object FullTimeUtils {
    fun getWeeks(weeks: String?): String? {
        if (weeks.isNullOrEmpty()) return null
        val spiltStr = spiltStr(weeks!!)
        val stringArray = ResourceUtil.getStringArray(R.array.weeks)
        val sb = StringBuffer()
        try {
            for (str in spiltStr) {
                val toInt = str.toInt()
                sb.append(stringArray[toInt])
                sb.append(",")
            }
            return sb.subSequence(0,sb.length-1).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getTimes(times: String?): String? {
        if (times.isNullOrEmpty()) return null
        val spiltStr = spiltStr(times!!)
        val stringArray = ResourceUtil.getStringArray(R.array.selectTimes)
        val sb = StringBuffer()
        try {
            for (str in spiltStr) {
                val toInt = str.toInt()
                sb.append(stringArray[toInt-8])
                sb.append(",")
            }
            return sb.subSequence(0,sb.length-1).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun spiltStr(value: String): List<String> {
        return value.split(",")
    }
}

