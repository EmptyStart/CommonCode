package com.tima.common.https

import io.reactivex.Observable
import okhttp3.MultipartBody
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
     * 文件上传
     */
    @POST("{path}")
    @Multipart
    fun upFile(@Path("path") path: String, @PartMap file: Array<MultipartBody.Part>?)
}
