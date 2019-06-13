package com.xx.common.base

/**
 * @author : zhijun.li on 2018/7/5
 *   email :
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