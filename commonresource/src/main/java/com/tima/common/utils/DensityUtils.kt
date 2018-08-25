package com.tima.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * 屏幕工具
 * Created by Administrator on 2018/8/25/025.
 */
object DensityUtils{

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     * 获取取屏幕宽度
     * @return
     */
    @SuppressLint("WrongConstant")
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }


    /**
     * 获取屏幕高度
     * @return
     */
    @SuppressLint("WrongConstant")
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    /**
     * 获取屏幕密度
     */
    fun getDensity(context: Context): Float{
        val dm = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(dm)
        return dm.density
    }

    /**
     * 控制语音长度显示，长度增幅随语音长度逐渐缩小
     */
    fun getVoiceWidth(length:Int) : Int{
        return (-0.04 * length * length+ 4.526 * length + 75.214).toInt()
    }
}