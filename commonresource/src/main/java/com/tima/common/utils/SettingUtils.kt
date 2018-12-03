package com.tima.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.preference.PreferenceManager
import com.tima.common.base.App
import android.content.ContextWrapper



/**
 * @author : zhijun.li on 2018/8/14
 *   email :
 *
 */
object SettingUtils {
    private val setting = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
    /**
     * 获取是否开启无图模式
     */
    fun getIsNoPhotoMode(): Boolean {
        val pm: Boolean by SpHelper("switch_noPhotoMode", false)
        return pm && NetWorkUtil.isMobile(App.app.applicationContext)
    }

//    /**
//     * 获取主题颜色
//     */
//    fun getColor(): Int {
//        val defaultColor = App.app.applicationContext.resources.getColor(R.color.colorPrimary)
//        val color = setting.getInt("color", defaultColor)
//        return if (color != 0 && Color.alpha(color) != 255) {
//            defaultColor
//        } else color
//    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        setting.edit().putInt("color", color).apply()
    }

    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return setting.getBoolean("nav_bar", false)
    }

    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        setting.edit().putBoolean("switch_nightMode", flag).apply()
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return setting.getBoolean("switch_nightMode", false)
    }

    /**
     * 获取是否开启自动切换夜间模式
     */
    fun getIsAutoNightMode(): Boolean {
        return setting.getBoolean("auto_nightMode", false)
    }

    fun getNightStartHour(): String {
        return setting.getString("night_startHour", "22")
    }

    fun setNightStartHour(nightStartHour: String) {
        setting.edit().putString("night_startHour", nightStartHour).apply()
    }

    fun getNightStartMinute(): String {
        return setting.getString("night_startMinute", "00")
    }

    fun setNightStartMinute(nightStartMinute: String) {
        setting.edit().putString("night_startMinute", nightStartMinute).apply()
    }

    fun getDayStartHour(): String {
        return setting.getString("day_startHour", "06")
    }

    fun setDayStartHour(dayStartHour: String) {
        setting.edit().putString("day_startHour", dayStartHour).apply()
    }

    fun getDayStartMinute(): String {
        return setting.getString("day_startMinute", "00")
    }

    fun setDayStartMinute(dayStartMinute: String) {
        setting.edit().putString("day_startMinute", dayStartMinute).apply()
    }

    /**
     *    跳转到设置开启权限
     */
    fun goPermisionSetting(context: Context) {
        var settingIntent = context.packageManager.getLaunchIntentForPackage("com.iqoo.secure")
        settingIntent?.let {
            context.startActivity(settingIntent)
            return
        }
        settingIntent = context.packageManager.getLaunchIntentForPackage("com.oppo.safe")
        settingIntent?.let {
            context.startActivity(settingIntent)
            return
        }
        settingIntent = Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
                .setData(Uri.fromParts("package", context.packageName, null))
        settingIntent?.let {
            context.startActivity(settingIntent)
        }
    }

    fun isFinished(activity: Activity): Boolean {
        return activity.isDestroyed || isFinished(activity)
    }

    fun getActivity(mContext: Context): Activity {
        var context=mContext
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = (context as ContextWrapper).baseContext
        }
        throw IllegalStateException("The view's Context is not an Activity.")
    }

    fun isMainProcess(context: Context) : Boolean{
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = am.runningAppProcesses
        val packageName = context.packageName
        val myPid = android.os.Process.myPid()
        runningAppProcesses.forEach {
            if (it.pid==myPid&&packageName.equals(it.processName)){
                return true
            }
        }
        return false
    }
}