package com.tima.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 *  键盘工具
 * Created by Administrator on 2018/8/24/024.
 */
object KeyboardUtils{

    /**
     * 隐藏软键盘
     * @param activity
     */
    @SuppressLint("WrongConstant")
    fun hideSoftInput(activity: Activity) {
        val manager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.window.attributes.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.currentFocus != null)
                manager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 显示软键盘
     */
    @SuppressLint("WrongConstant")
    fun showSoftInput(activity: Activity) {
        val manager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.window.attributes.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.currentFocus != null)
                manager.showSoftInput(activity.currentFocus, InputMethodManager.SHOW_FORCED)
        }
    }
}