package com.tima.code.views.fragments

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import  kotlinx.android.synthetic.main.code_fragment_fragmentutils.*
/**
 * @author : zhijun.li on 2018/8/24
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.fragmentutils)
class  FragmentUtils : BaseFragment() {
    @Autowired
    @JvmField
    var title : String="baby"

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_fragmentutils
    }

    override fun initView() {
//        title=arguments.get("title") as String
        title?.let {
            tv_frag1.text=it
        }
    }

    override fun lazyLoad() {

    }
}