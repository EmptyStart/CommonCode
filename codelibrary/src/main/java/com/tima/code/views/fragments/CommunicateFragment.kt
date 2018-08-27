package com.tima.code.views.fragments

import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths

/**
 * 沟通
 * Created by Administrator on 2018/8/27/027.
 */
@Route(path = RoutePaths.communicatefragment)
class CommunicateFragment : BaseFragment(){
    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_communicate
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }

}