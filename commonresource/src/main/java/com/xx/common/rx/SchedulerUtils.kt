package com.xx.common.rx

import com.xx.common.rx.scheduler.IoMainScheduler

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