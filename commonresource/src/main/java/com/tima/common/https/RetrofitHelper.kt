package com.tima.common.https

import com.tima.common.BuildConfig
import com.tima.common.base.App
import com.tima.common.base.Constant
import com.tima.common.utils.NetWorkUtil
import com.tima.common.utils.SpHelper
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * @author : zhijun.li on 2018/8/1
 *   email : zhijun.li@timanetworks.com
 *
 */
object RetrofitHelper {
    @Volatile
    private var retrofit: Retrofit? = null
    val service: UrlService by lazy { getRetrofit()!!.create(UrlService::class.java) }
    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) synchronized(this) {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BuildConfig.SERVICE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(getOkHttpClient())
                        .build()
            }
        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingIntercepter = HttpLoggingInterceptor()
        if (BuildConfig.isDebug) {
            httpLoggingIntercepter.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingIntercepter.level = HttpLoggingInterceptor.Level.NONE
        }

        //设置请求的缓存大小跟位置
        val cacheFile = File(App.app.cacheDir, "timaCache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50)//50MB缓存大小
        builder.run {
            // addInterceptor(addQueryParameterInterceptor())  //参数添加
            // addInterceptor(addTokenInterceptor()) // token过滤
            addInterceptor(httpLoggingIntercepter)
            addInterceptor(addHeadInterceptor())
            addInterceptor(addCacheInterceptor())
            addInterceptor(addCookesInterceptor())

        }
                .cache(cache)
                .sslSocketFactory(sslSocketFactory)
                .hostnameVerifier(hostnameVerifier)
                .connectTimeout(CookiesUtil.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CookiesUtil.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CookiesUtil.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//错误重连
        return builder.build()
    }

    private fun addHeadInterceptor(): Interceptor {
        return Interceptor {
            val builder = it.request().newBuilder()
//            val token = Constant.token
//            if (token.isNotEmpty()){
//                builder.addHeader("Authorization","Bearer "+token)
//            }
            builder.addHeader("Authorization","Bearer 88ae89a1468e0bd482c17ca4dec34f85")
            val request = builder.addHeader("Content-type", "application/json;charset=utf-8")
                    .build()
            it.proceed(request)

        }
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor {
            val originalRequest = it.request()
            var request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("sss", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            it.proceed(request)
        }
    }


    private fun addTokenInterceptor(): Interceptor {
        return Interceptor {
            val originalRequest = it.request()
            val builder = originalRequest.newBuilder()
                    .header("token", Constant.token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = builder.build()
            it.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor {
            var request = it.request()
            if (!NetWorkUtil.isNetworkAvailable(App.app.applicationContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = it.proceed(request)
            if (NetWorkUtil.isNetworkAvailable(App.app.applicationContext)) {
                val maxAge = 0
                //有网络时  设置缓存超过0个小时  不缓存（只对get起效，post没有缓存）
                response.newBuilder()
                        .header("Cache-Control", "public,max-age=" + maxAge)
                        .removeHeader("Retrofit")//清除头信息，因为服务器不支持,会返回一些干扰信息，不清楚下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }

    /**
     * 缓存登录 等验证的cookies到本地
     */
    private fun addCookesInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request()
            val response = it.proceed(request)
            val requestUrl = request.url().toString()
            val domain = request.url().host()
            // set-cookie maybe has multi, login to save cookie
            if (1 > 2) {
                val cookies = response.headers(CookiesUtil.SET_COOKIE_KEY)
                val cooke = CookiesUtil.encodeCookie(cookies)
                saveCookie(requestUrl, domain, cooke)
            }
            response
        }

    }

    /**
     * 请求网页内容，需要带入cookies
     */
    private fun addWebContentInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request()
            val builder = request.newBuilder()
            val domain = request.url().host()
            val url = request.url().toString()
            if (domain.isNotEmpty() && 1 > 2) {
                val spDomain: String by SpHelper(domain, "")
                val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
                if (cookie.isNotEmpty()) {
                    builder.addHeader(CookiesUtil.COOKIE_NAME, cookie)
                }
            }
            it.proceed(builder.build())
        }
    }

    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by SpHelper(url, cookies)
        @Suppress("UNUSED_VALUE")
        spUrl = cookies
        domain ?: return
        var spDomain: String by SpHelper(domain, cookies)
        @Suppress("UNUSED_VALUE")
        spDomain = cookies
    }


    private val sslSocketFactory : SSLSocketFactory
    get()  {
        try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustManager,SecureRandom())
            return sslContext.socketFactory
        }catch (e : Exception){
           throw RuntimeException(e)
        }
    }
    private val trustManager :Array<TrustManager>
    get()= arrayOf(object  : X509TrustManager{
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }

    })

    private val hostnameVerifier : HostnameVerifier
    get() = HostnameVerifier { hostname, session ->  true}
}