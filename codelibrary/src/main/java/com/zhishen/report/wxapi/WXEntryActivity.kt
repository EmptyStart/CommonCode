package com.zhishen.report.wxapi

import android.support.v7.app.AppCompatActivity
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/**
 * @author : zhijun.li on 2018/10/10
 *   email : zhijun.li@timanetworks.com
 *
 */
class WXEntryActivity  : AppCompatActivity(), IWXAPIEventHandler {
    override fun onResp(p0: BaseResp?) {
    }

    override fun onReq(p0: BaseReq?) {
    }
}