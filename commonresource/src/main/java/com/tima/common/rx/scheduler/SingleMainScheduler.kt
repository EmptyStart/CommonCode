package com.tima.common.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author : zhijun.li on 2018/8/15
 *   email : zhijun.li@timanetworks.com
 *
 */
class SingleMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.single(), AndroidSchedulers.mainThread())
