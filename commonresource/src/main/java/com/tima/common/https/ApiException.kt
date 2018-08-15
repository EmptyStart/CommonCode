package com.tima.common.https

import java.lang.Exception

/**
 * @author : zhijun.li on 2018/7/3
 *   email : zhijun.li@timanetworks.com
 *
 */
class ApiException constructor(): Exception(){
    var errorCode : String?=null
    constructor(message:String) : this() {
        super.message
    }
    constructor(errorCode :String,message: String) : this(){
        super.message
        this.errorCode=errorCode
    }
    constructor(e :Throwable?) : this(){
        super.cause
    }

}
class Error{
    companion object {
        /**
         * 未知错误
         */
        val UNKNOWN=1000
        /**
         * 解析错误
         */
        val PARSE_ERROR= UNKNOWN+1
        /**
         * 网络错误
         */
        val NETWORD_ERROR = PARSE_ERROR + 1
        /**
         * 协议出错
         */
        val HTTP_ERROR = NETWORD_ERROR + 1
        /**
         * 证书出错
         */
        val SSL_ERROR = HTTP_ERROR + 1
        /**
         * 连接超时
         */
        val TIMEOUT_ERROR = SSL_ERROR + 1
        /**
         * 调用错误
         */
        val INVOKE_ERROR = TIMEOUT_ERROR + 1
        /**
         * 类转换错误
         */
        val CAST_ERROR = INVOKE_ERROR + 1
        /**
         * 请求取消
         */
        val REQUEST_CANCEL = CAST_ERROR + 1
        /**
         * 未知主机错误
         */
        val UNKNOWNHOST_ERROR = REQUEST_CANCEL + 1
        /**
         * 空指针错误
         */
        val NULLPOINTER_EXCEPTION = UNKNOWNHOST_ERROR + 1
    }
}