package com.tima.code.views.fragments

import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import  kotlinx.android.synthetic.main.code_fragment_fragmentutils.*
/**
 * @author : zhijun.li on 2018/8/24
 *   email : zhijun.li@timanetworks.com
 *
 */
//@Route(path = RoutePaths.fragmentutils)
class FragmentUtils : BaseFragment() {
//    @Autowired(name = "title")
//    @JvmField
//    var title : String=""

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_fragmentutils
    }

    override fun initView() {
//        tv_frag1.text=title
    }

    override fun lazyLoad() {

    }
}