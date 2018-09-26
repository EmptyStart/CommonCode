package com.zhishen.report

import android.content.Context
import com.tencent.android.tpush.*
import com.tima.common.utils.LogUtils

/**
 * @author : zhijun.li on 2018/9/26
 *   email : zhijun.li@timanetworks.com
 *
 */
class MessageReceiver : XGPushBaseReceiver(){
    override fun onSetTagResult(p0: Context?, p1: Int, p2: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNotifactionShowedResult(p0: Context?, p1: XGPushShowedResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUnregisterResult(p0: Context?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteTagResult(p0: Context?, p1: Int, p2: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRegisterResult(p0: Context?, p1: Int, p2: XGPushRegisterResult?) {
        if (p2!=null) {
            LogUtils.i("MessageReceiver", p2.toString())
        }
    }

    override fun onTextMessage(p0: Context?, p1: XGPushTextMessage?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNotifactionClickedResult(p0: Context?, p1: XGPushClickedResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}