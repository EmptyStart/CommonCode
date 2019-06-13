package com.xx.common.utils

import android.app.Dialog
import android.content.Context
import com.xx.common.views.LoadingView


/**
 * @author : zhijun.li on 2018/9/19
 *   email :
 *
 */
class LoadingBarManage(context: Context) {
    private var loadingCount = 0
    private var mDialog: Dialog = LoadingView(context)

    init {
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)
    }


    fun show() {
        if (mDialog.isShowing) {
            loadingCount++
            return
        }
        mDialog.show()
    }

    fun dismiss() {
        if (loadingCount > 0) {
            loadingCount--
            return
        }
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

    fun destroy() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

}