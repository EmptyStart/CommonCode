package com.xx.code.views.fragments

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.common.base.BaseFragment
import com.xx.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_fragment_release.*

/**
 * @author : zhijun.li on 2018/8/27
 *   email :
 *
 */
@Route(path = RoutePaths.releasefragment)
class ReleaseFragment : BaseFragment() {
    override fun attachLayoutRes(): Int {

        return R.layout.code_fragment_release
    }

    override fun initView() {
        release_fulltime.setOnClickListener {
            ARouter.getInstance().build(RoutePaths.releasefulltime).navigation()
        }
        release_parttime.setOnClickListener {
            ARouter.getInstance().build(RoutePaths.releaseparttime).navigation()
        }
    }

    override fun lazyLoad() {
    }
}