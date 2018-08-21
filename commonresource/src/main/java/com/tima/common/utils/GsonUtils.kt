package com.tima.common.utils

import com.google.gson.Gson

/**
 * @author : zhijun.li on 2018/8/20
 *   email : zhijun.li@timanetworks.com
 *
 */
object GsonUtils {
    val getGson: Gson by lazy { Gson() }
}