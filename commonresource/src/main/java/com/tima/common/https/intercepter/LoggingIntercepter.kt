package com.tima.common.https.intercepter

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author : zhijun.li on 2018/7/2
 *   email :
 *
 */
class LoggingIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request =chain!!.request()
        val startTime = System.nanoTime()
        Log.d(javaClass.simpleName, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        val response=chain!!.proceed(request)
        val endTime = System.nanoTime();
        Log.d(javaClass.simpleName, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (endTime - startTime) / 1e6, response.headers()));
        return response
    }
}