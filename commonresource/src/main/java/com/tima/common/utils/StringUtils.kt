package com.tima.common.utils

import android.text.TextUtils

/**
 * Created by Administrator on 2018/8/23/023.
 */
object StringUtils{

    /**
     * 判断两个字符串是否相等
     * @return
     */
    fun isEquals(data1: String, data2: String): Boolean {
        if (TextUtils.isEmpty(data1) || TextUtils.isEmpty(data2)) {
            return false
        }
        if (data1 == data2)
            return true
        return false
    }


    /**
     * 判断两个字符串 不为空且不相等
     * @return
     */
    fun isUnEquals(data1: String, data2: String): Boolean {
        if (TextUtils.isEmpty(data1) || TextUtils.isEmpty(data2)) {
            return false
        }
        if (data1 != data2)
            return true
        return false
    }

    /**
     * 截取 字符串
     * @return
     */
    fun getSubString(time: String, start: Int, end: Int): String {
        var str = ""
        if (!TextUtils.isEmpty(time)) {
            if (time.length >= end) {
                str = time.substring(start, end)
            }
        }
        return str
    }
}