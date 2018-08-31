package com.tima.code.views.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.tima.code.R

/**
 * Created by Administrator on 2018/8/31/031.
 */
object DialogUtils{

    /**
     * 支付结果--弹出框
     */
    fun showPaymentResultDialog(activity: Activity) : Dialog{
        var view = View.inflate(activity, R.layout.code_dialog_payment_result,null)
        var dialog = AlertDialog.Builder(activity).create()
        var tv_payment_order : TextView = view.findViewById(R.id.tv_payment_order)
        var tv_account_balance : TextView = view.findViewById(R.id.tv_account_balance)
        var tv_sure : TextView = view.findViewById(R.id.tv_sure)
        tv_sure.setOnClickListener(View.OnClickListener {
            if (dialog != null)
                dialog.dismiss()
        })

        showDialog(activity,dialog,view)
        return dialog
    }

    /**
     * 账户充值
     */
    fun showAccountRecharge(activity: Activity,listener : OnDialogListener) : Dialog{
        var view = View.inflate(activity, R.layout.code_dialog_account_recharge,null)
        var dialog = AlertDialog.Builder(activity).create()
        var tv_sure_recharge : TextView = view.findViewById(R.id.tv_sure_recharge)
        var iv_cancel : ImageView = view.findViewById(R.id.iv_cancel)

        iv_cancel.setOnClickListener(View.OnClickListener {
            if (dialog != null)
                dialog.dismiss()
        })
        tv_sure_recharge.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onDialogClick()
        })
        showKeyboardDialog(activity,dialog,view)
        return dialog
    }

    interface OnDialogListener{
        fun onDialogClick()
    }


    fun showDialog(activity: Activity,dialog : Dialog,view: View){
        dialog.setCancelable(false)
        dialog.show()
        var windowManager : WindowManager = activity.windowManager
        var display : Display = windowManager.defaultDisplay
        var params : WindowManager.LayoutParams = dialog.window.attributes
        params.width = (display.width * 0.85).toInt()
        dialog.window.attributes = params
        dialog.window.setContentView(view)
    }

    /**
     * 显示软键盘 Dialog
     */
    fun showKeyboardDialog(activity: Activity,dialog : Dialog,view: View){
        dialog.setCancelable(false)
        dialog.show()

        dialog.window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)                  //显示软键盘
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        var windowManager : WindowManager = activity.windowManager
        var display : Display = windowManager.defaultDisplay
        var params : WindowManager.LayoutParams = dialog.window.attributes
        params.width = (display.width * 0.85).toInt()
        dialog.window.attributes = params
        dialog.window.setContentView(view)
    }
}