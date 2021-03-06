package com.zhishen.report

import android.content.Context
import com.tencent.android.tpush.*
import com.xx.common.utils.LogUtils

/**
 * @author : zhijun.li on 2018/9/26
 *   email :
 *
 */
class MessageReceiver : XGPushBaseReceiver(){
    override fun onSetTagResult(p0: Context?, p1: Int, p2: String?) {
    }

    override fun onNotifactionShowedResult(p0: Context?, p1: XGPushShowedResult?) {
    }

    override fun onUnregisterResult(p0: Context?, p1: Int) {
    }

    override fun onDeleteTagResult(p0: Context?, p1: Int, p2: String?) {
    }

    override fun onRegisterResult(p0: Context?, p1: Int, p2: XGPushRegisterResult?) {
        if (p2!=null) {
            LogUtils.i("MessageReceiver", p2.toString())
        }
    }

    override fun onTextMessage(p0: Context?, p1: XGPushTextMessage?) {
    }

    override fun onNotifactionClickedResult(p0: Context?, p1: XGPushClickedResult?) {
    }
}