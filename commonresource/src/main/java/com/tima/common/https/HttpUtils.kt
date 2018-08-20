package com.tima.common.https

import com.tima.common.https.intercepter.LoggingIntercepter
import okhttp3.*
import java.io.IOException

/**
 * @author : zhijun.li on 2018/7/2
 *   email : zhijun.li@timanetworks.com
 *
 */
class HttpUtils private constructor(){
    val TAG ="HttpUtils"
    val instance : HttpUtils by lazy (mode=LazyThreadSafetyMode.SYNCHRONIZED)  {
        HttpUtils()
    }
    fun get(url: String)  {

    }

}
