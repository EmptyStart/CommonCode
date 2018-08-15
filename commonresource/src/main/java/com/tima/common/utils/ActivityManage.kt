package com.tima.common.utils

import android.app.Activity
import java.util.*

/**
 * @author : zhijun.li on 2018/6/27
 *   email : zhijun.li@timanetworks.com
 *
 */
class ActivityManage private constructor() {
    companion object {
        /**
         * this Activity arrays
         */
        private var stackActivity: Stack<Activity?> = Stack()
        val instance: ActivityManage by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManage()
        }
    }
    /**
     * Each created Activity adds an array, and if you delete the previous one repeatedly,
     * add the latest Activity to the top of the stack
     */
    fun addActivity(activity: Activity?) {
        if (stackActivity.contains(activity)) {
            stackActivity.remove(activity)
        }
        stackActivity.push(activity)
    }

    /**
     * If this arrays contains Activity ,remove it
     */
    fun removeActivity(activity: Activity?) {
        if (stackActivity.contains(activity)) {
            stackActivity.remove(activity)
        }
    }

    /**
     * Iterate through the array, calling the 'finish()' method for all activities in the
     * array. Empty array
     */
    fun exitAll() {
        stackActivity.forEach {
            it?.finish()
        }
        stackActivity.clear()
    }
    fun getCurrentActivity() : Activity?= stackActivity.lastElement()

}