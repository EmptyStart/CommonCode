package com.tima.common.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.google.gson.JsonSyntaxException





/**
 * @author : zhijun.li on 2018/8/20
 *   email :
 *
 */
object GsonUtils {
    val getGson: Gson by lazy { Gson() }
}