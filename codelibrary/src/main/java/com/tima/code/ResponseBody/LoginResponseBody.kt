package com.tima.code.ResponseBody

import com.google.gson.annotations.SerializedName
import com.tima.common.https.ApiException
import okhttp3.MediaType
import okio.BufferedSource

/**
 * @author : zhijun.li on 2018/9/3
 *   email : zhijun.li@timanetworks.com
 *
 */

data class LoginResponseBody (
    val hr: Hr,
    val company: String,
    val token: String,
    val img_base: String,
    val parttime_commition: Double
)

data class Hr(
    val id: Int,
    val created_on: String,
    val mobile: String,
    val name: Any,
    val nick_name: Any,
    val password: Any,
    val avatar: Any,
    val type: String,
    val gender: String,
    val address: Any,
    val province: Any,
    val city: Any,
    val region: Any,
    val email: Any,
    val birthday: Any,
    val rating: Any,
    val status: String,
    val register_ip: Any,
    val last_login_ip: String,
    val last_login_on: Any,
    val last_login_device: Any,
    val tel: Any,
    val img1: Any,
    val img2: Any,
    val img3: Any,
    val id_card: Any,
    val id_verified: String,
    val push_id: Any,
    val im_id: Any,
    val company: Int
)

data class RegisterValidResponseBody(
    val detail: String
)