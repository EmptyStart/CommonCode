package com.tima.common.https

import io.reactivex.functions.Consumer
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/8/15
 *   email : zhijun.li@timanetworks.com
 *
 */
class BaseSubscriber : Consumer<ResponseBody> {
    override fun accept(t: ResponseBody?) {

    }
}