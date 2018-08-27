package com.tima.chat.helper

import com.hyphenate.chat.ChatClient
import com.hyphenate.chat.EMClient
import com.hyphenate.cloud.HttpClientConfig
import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.IOException

/**
 * Created by Administrator on 2018/8/27/027.
 */
object FileUploadManager{

    val SERVER_URL = HttpClientConfig.getBaseUrlByAppKey() + "/"

    internal var mRetrofit: Retrofit? = null

    fun retrofit(): Retrofit {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericClient()) // Set the custom client when building adapter
                    .build()
        }
        return mRetrofit!!
    }

    fun genericClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer " + ChatClient.getInstance().accessToken())
                            .addHeader("restrict-access", "true")
                            .build()
                    chain.proceed(request)
                }.build()
    }


    interface FileUploadService {

        /**
         * 单文件上传
         * @param file
         * *
         * @return
         */
        @Multipart
        @POST("chatfiles")
        fun upload(@Part file: MultipartBody.Part): Call<ResponseBody>

    }


}