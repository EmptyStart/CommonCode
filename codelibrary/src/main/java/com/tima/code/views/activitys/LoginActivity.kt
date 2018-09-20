package com.tima.code.views.activitys

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.timaconstracts.ILoginView
import com.tima.code.timapresenter.LoginPresenterImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ResourceUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.code_activity_login.*
import org.jetbrains.anko.toast
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.login)
class LoginActivity : BaseActivity(), ILoginView, View.OnClickListener {
    override fun cancelValid() {
        tv_vaild.text = "获取验证码"
        tv_vaild.isEnabled = true
        tv_vaild.setTextColor(ResourceUtil.getColorId(R.color.code_title_barbc))
        mSubscription?.cancel()
    }

    var mSubscription: Subscription? = null

    override fun getValidSuccess() {
        val count = 59L
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .onBackpressureBuffer()
                .take(count)
                .map { aLong ->
                    count - aLong
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Long> {
                    override fun onComplete() {
                        cancelValid()
                    }

                    override fun onSubscribe(s: Subscription?) {
                        tv_vaild.isEnabled = false
                        tv_vaild.setTextColor(Color.GRAY)
                        mSubscription = s
                        s?.request(Long.MAX_VALUE)//设置请求事件的数量，重要，必须调用
                    }

                    override fun onNext(t: Long?) {
                        tv_vaild.text = t.toString() + "s后重发"//接受到一条就是会操作一次UI
                    }

                    override fun onError(t: Throwable?) {
                        t?.printStackTrace()
                    }

                })
    }

    override fun mobile(): String? {

        return et_username.text.toString().trim()
    }

    override fun valid(): String? {
        return et_password.text.toString().trim()
    }

    override fun onClick(v: View?) {
        mPresent.onClick(v)
    }

    val mPresent by lazy(LazyThreadSafetyMode.NONE) {
        LoginPresenterImpl(this)
    }

    override fun showLoading() {
        loadingBar.show()
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun getLayoutId(): Int {

        return R.layout.code_activity_login
    }

    override fun inits(savedInstanceState: Bundle?) {
        lifecycle.addObserver(mPresent)
        tv_vaild.setOnClickListener(this)
        tv_login.setOnClickListener(this)
    }

}