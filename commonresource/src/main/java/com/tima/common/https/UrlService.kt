package com.tima.common.https

import io.reactivex.Observable
import okhttp3.Call
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author : zhijun.li on 2018/8/10
 *   email : zhijun.li@timanetworks.com
 *
 */
interface UrlService{
    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @POST("user/login")
    @FormUrlEncoded
    fun userLogin(@Field("username") username: String,
                        @Field("password") password: String): Observable<ResponseBody>
    /**
     * https://api4c-dev.mowei.net/api/v1/apply/?position__type=0
     *  @param page
     */
    @GET("uap/manage/getInitAdvert/{path}")
    fun executeGet(@Path("path") path: String,@QueryMap queryMap: Map<String,String>):
            Observable<ResponseBody>
}