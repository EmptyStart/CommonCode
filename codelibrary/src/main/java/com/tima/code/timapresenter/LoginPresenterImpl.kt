package com.tima.code.timapresenter

import android.view.View
import com.tima.code.timaconstracts.ILoginPresent
import com.tima.code.timaconstracts.ILoginView
import com.tima.code.timaviewmodels.LoginViewModelImpl
import com.tima.common.base.IDataListener
import okhttp3.ResponseBody

/**
 * @author : zhijun.li on 2018/8/30
 *   email : zhijun.li@timanetworks.com
 *
 */
class LoginPresenterImpl : ILoginPresent {
    var mView: ILoginView? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { LoginViewModelImpl() }

    constructor(mView: ILoginView) {
        this.mView = mView
    }


    override fun onClick(view: View?) {
        mViewMode.addOnVerifyListener(object: IDataListener {
            override fun successData(success: ResponseBody) {

            }

            override fun errorData(error: String) {

            }

            override fun requestData(): Map<String, String>? {

                return mapOf(Pair("mobile","17615009211"))
            }
        })
    }


}