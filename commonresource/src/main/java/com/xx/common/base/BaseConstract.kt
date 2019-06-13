package com.xx.common.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zhijun.li on 2018/9/4
 *   email :
 *
 */
 class BasePresenter<mView : IBaseViews> {

}

 open class BaseViewModel {
    /**
     * set 集合存放可以取消的 observe
     */
    private var compositeDisposable = CompositeDisposable()

    /**
     * 添加可以存放的observe
     */
    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /**
     * 在onDestroy() 调用
     */
    fun detachView() {
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }


}