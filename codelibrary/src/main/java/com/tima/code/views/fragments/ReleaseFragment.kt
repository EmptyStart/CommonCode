package com.tima.code.views.fragments

import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths

/**
 * @author : zhijun.li on 2018/8/27
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.releasefragment)
class ReleaseFragment : BaseFragment() {
    override fun attachLayoutRes(): Int {

        return R.layout.code_fragment_release
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }
}