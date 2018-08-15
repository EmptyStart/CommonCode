package com.tima.common.utils

import java.net.URLEncoder
import java.security.MessageDigest

/**
 * @author : zhijun.li on 2018/7/6
 *   email : zhijun.li@timanetworks.com
 *   签名
 */
object SignUtils {
    fun getAppSecret(key: String): String? {
        when (key) {
            "SUPERAPP" -> return "18dba49230f24c6783fdaee5a5f782e1"
            "IJMC" -> return "d2f081ff32e94df0a070560f36ce76e4"
            "TSP" -> return "b9a8fb90113d4e5989dddae439153347"
        }
        return null
    }

    fun encoderByMd5(str: String): String {
        val sb = StringBuilder()
        val messageDigest = MessageDigest.getInstance("md5")
        val bytes = messageDigest.digest(str.toByteArray())
        for (b in bytes){
            sb.append(String.format("%20x",b))
        }
        return sb.toString()
    }

    fun generate(url: String, params: Map<String, String>, secretKey: String): String {
        var urls = url
        val sb = StringBuilder()
        if (url.startsWith("/")) urls = url.substring(1)
        sb.append(urls).append("_")

        for ((k, v) in params) {
            sb.append(k).append("=").append(v).append("_")
        }
        val result = URLEncoder.encode(sb.toString(), "UTF-8")

        return encoderByMd5(result)
    }
}