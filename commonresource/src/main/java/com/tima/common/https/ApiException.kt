package com.tima.common.https


/**
 * @author : zhijun.li on 2018/7/3
 *   email : zhijun.li@timanetworks.com
 *
 */

data class ApiException(
    val code: Int,
    val detail: String
)