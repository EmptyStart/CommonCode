package com.tima.common.base

/**
 * Created by Administrator on 2018/8/24/024.
 */
class MsgEventData{
    var functionName: String? = null
    var paras: Array<String>? = null

    constructor(functionName: String?,  paras: Array<String>?) {
        this.functionName = functionName
        this.paras = paras
    }
}