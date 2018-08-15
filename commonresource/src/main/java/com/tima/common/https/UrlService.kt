package com.tima.common.https

import io.reactivex.Observable
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
     *  获取收藏列表
     *  http://www.wanandroid.com/lg/collect/list/0/json
     *  @param page
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Observable<ResponseBody>
}