package com.tima.common.base

/**
 * @author : zhijun.li on 2018/7/5
 *   email : zhijun.li@timanetworks.com
 *
 */

data class BaseResponse(
        val errors: List<Error>,
        val status: String
)

data class Error(
    val errcode: String,
    val errmsg: String,
    val field: String
)