package com.tima.code.views.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.tima.code.R

/**
 *  支付结果--弹出框
 * Created by Administrator on 2018/8/30/030.
 */

class PaymentResultDialog{

    var activity : Activity
    constructor(activity: Activity){
        this.activity = activity
    }

    fun showDialog(){
        var view = View.inflate(activity, R.layout.code_dialog_payment_result,null)
        var dialog = AlertDialog.Builder(activity).create()
        var tv_payment_order : TextView = view.findViewById(R.id.tv_payment_order)
        var tv_account_balance : TextView = view.findViewById(R.id.tv_account_balance)
        var tv_sure : TextView = view.findViewById(R.id.tv_sure)
        tv_sure.setOnClickListener(View.OnClickListener {
            if (dialog != null)
                dialog.dismiss()
        })

        dialog.setCancelable(false)
        dialog.show()
        var windowManager : WindowManager = activity.windowManager
        var display : Display = windowManager.defaultDisplay
        var params : WindowManager.LayoutParams = dialog.window.attributes
        params.width = (display.width * 0.8).toInt()
        dialog.window.attributes = params
        dialog.window.setContentView(view)
    }
}