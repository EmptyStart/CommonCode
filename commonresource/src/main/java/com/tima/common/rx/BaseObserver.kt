package com.tima.common.rx

import com.tima.common.base.IBaseViews
import io.reactivex.observers.ResourceObserver

/**
 * @author : zhijun.li on 2018/8/15
 *   email :
 *
 */
open class BaseObserver<T> : ResourceObserver<T> {

    private var mView: IBaseViews? = null
    private var mErrorMsg: String = ""

    constructor(view: IBaseViews) {
        this.mView = view
    }

    constructor(view: IBaseViews, errorMsg: String) {
        this.mView = view
        this.mErrorMsg = errorMsg
    }

    override fun onComplete() {
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
        mView ?: return
        if (mErrorMsg.isNotEmpty()) {
            mView?.showError(mErrorMsg)
        } else {
//            mErrorMsg = ExceptionHandle.handleException(e)
            mView?.showError(mErrorMsg)
        }
    }

}