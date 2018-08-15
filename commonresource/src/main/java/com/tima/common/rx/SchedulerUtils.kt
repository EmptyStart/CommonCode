package com.tima.common.rx

/**
 * @author : zhijun.li on 2018/8/15
 *   email : zhijun.li@timanetworks.com
 *
 */
object SchedulerUtils{
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}