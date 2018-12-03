package com.tima.common.rx

import com.tima.common.rx.scheduler.IoMainScheduler

/**
 * @author : zhijun.li on 2018/8/15
 *   email :
 *
 */
object SchedulerUtils{
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}