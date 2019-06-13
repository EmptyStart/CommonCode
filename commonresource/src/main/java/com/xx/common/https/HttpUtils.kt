package com.xx.common.https

/**
 * @author : zhijun.li on 2018/7/2
 *   email :
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
