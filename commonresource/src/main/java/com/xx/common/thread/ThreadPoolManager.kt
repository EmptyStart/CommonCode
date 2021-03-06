package com.xx.common.thread

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * 线程池管理 管理整个项目中所有的线程，所以不能有多个实例对象
 * Created by Administrator on 2018/9/18.
 */
class ThreadPoolManager {
    /**
     * 单例设计模式（饿汉式）
     * 单例首先私有化构造方法，然后饿汉式一开始就开始创建，并提供get方法
     */

    companion object {
        var mInstance = ThreadPoolManager()
        fun getInstance(): ThreadPoolManager {
            return mInstance
        }
    }

    var corePoolSize: Int = 0                                                               //核心线程池的数量，同时能够执行的线程数量
    var maximumPoolSize: Int = 0                                                           //最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
    val keepAliveTime: Long = 1                                                             //存活时间
    val unit = TimeUnit.HOURS
    var executor: ThreadPoolExecutor

    constructor() {
        /**
         * 给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
         */
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1
        maximumPoolSize = corePoolSize                                                      //虽然maximumPoolSize用不到，但是需要赋值，否则报错
        executor = ThreadPoolExecutor(
                corePoolSize,                                                               //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                maximumPoolSize,                                                            //5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                keepAliveTime,                                                              //表示的是maximumPoolSize当中等待任务的存活时间
                unit,
                LinkedBlockingQueue<Runnable>(),                                            //缓冲队列，用于存放等待任务，Linked的先进先出
                Executors.defaultThreadFactory(),                                           //创建线程的工厂
                ThreadPoolExecutor.AbortPolicy()                                            //用来对超出maximumPoolSize的任务的处理策略
        )
        executor = Executors.newFixedThreadPool(50) as ThreadPoolExecutor
    }

    /**
     * 执行任务
     */
    fun execute(runnable: Runnable?) {
        if (runnable == null) return
        executor.execute(runnable)
    }

    /**
     * 从线程池中移除任务
     */
    fun remove(runnable: Runnable?) {
        if (runnable == null) return
        executor.remove(runnable)
    }

}