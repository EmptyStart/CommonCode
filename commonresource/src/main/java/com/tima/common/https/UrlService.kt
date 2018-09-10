package com.tima.common.https

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author : zhijun.li on 2018/8/10
 *   email : zhijun.li@timanetworks.com
 *
 */
interface UrlService {
    /**
     *post请求
     */
    @POST("{path}")
    @FormUrlEncoded
    fun executePost(@Path("path") path: String, @FieldMap fieldMap: Map<String, String>?, @QueryMap
    queryMap: Map<String, String>?): Observable<ResponseBody>

    /**
     *post请求
     */
    @POST("{path}")
    @FormUrlEncoded
    fun executePost(@Path("path") path: String, @FieldMap fieldMap: Map<String, String>?): Observable<ResponseBody>

    /**
     *get请求
     */
    @GET("{path}")
    fun executeGet(@Path("path") path: String, @QueryMap queryMap: Map<String, String>?):
            Observable<ResponseBody>
/**
     *get请求
     */
    @GET("{path}")
    fun executeGet(@Path("path") path: String): Observable<ResponseBody>


    /**
     * 文件上传
     */
    @POST("{path}")
    @Multipart
    fun upFile(@Path("path") path: String, @PartMap file: Array<MultipartBody.Part>?): Observable<ResponseBody>

    /**
     * patch请求
     */
    @PATCH("{path}")
    @FormUrlEncoded
    fun executePatch(@Path("path") path: String, @FieldMap fieldMap: Map<String, String>?):
            Observable<ResponseBody>

    /**
     * patch请求
     * 上传图片
     */
    @PATCH("{path}")
    @Multipart
    fun executePatch(@Path("path") path: String, @Part file: MultipartBody.Part?, @Part
    type: MultipartBody.Part?, @Part ext: MultipartBody.Part?): Observable<ResponseBody>

    /**
     * patch请求
     */
    @PATCH("{path}")
    @Multipart
    fun executePatch(@Path("path") path: String, @FieldMap fieldMap: Map<String, String>?, @QueryMap
    queryMap: Map<String, String>?): Observable<ResponseBody>
}

enum class HttpRequest {
    POST,
    GET,
    DELETE
}