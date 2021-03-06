package com.xx.code.utils

import com.xx.code.R
import com.xx.common.utils.ResourceUtil

/**
 * @author : zhijun.li on 2018/10/12
 *   email :
 *
 */
object FullTimeUtils {
    fun getWeeks(weeks: String?): String? {
        if (weeks.isNullOrEmpty()) return null
        var spiltStr = spiltStr(weeks!!)
        spiltStr = spiltStr.sorted()
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
            val arrayTimes = arrayListOf<Int>()
            for (str in spiltStr) {
                val toInt = str.toInt()
                arrayTimes.add(toInt-8)
            }
            val sorted = arrayTimes.sorted()
            for (str in sorted) {
                sb.append(stringArray[str])
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

