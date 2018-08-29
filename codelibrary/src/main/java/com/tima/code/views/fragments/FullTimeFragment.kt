package com.tima.code.views.fragments

import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths

/**
 *  管理-全职
 * Created by Administrator on 2018/8/28/028.
 */
@Route(path = RoutePaths.manage_fulltime_fragment)
class FullTimeFragment : BaseFragment(){
    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_full_time
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }

}