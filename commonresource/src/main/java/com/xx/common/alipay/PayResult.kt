package com.xx.common.alipay

import android.text.TextUtils

/**
 * @author : zhijun.li on 2018/9/14
 *   email :
 *
 */
class PayResult(rawResult: Map<String, String>?) {
    private var resultStatus: String? = null
    private var result: String? = null
    private var memo: String? = null

    init {
        rawResult?.apply {
            keys.forEach {
                if (TextUtils.equals(it, "resultStatus")) {
                    resultStatus = get("resultStatus")
                } else if (TextUtils.equals(it, "result")) {
                    result = get("result")
                } else if (TextUtils.equals(it, "memo")) {
                    memo = get("memo")
                }
            }
        }
    }

    override fun toString(): String {
        return "resultStatus={" + resultStatus + "};memo={" + memo + "};result={" + result + "}"
    }

    fun getResultStatus(): String? {
        return resultStatus
    }

    fun getMemo(): String? {
        return memo
    }

    fun getResult(): String? {
        return result
    }
}