package com.tima.code.views.fragments

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import  kotlinx.android.synthetic.main.code_fragment_fragmentutils.*
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/24
 *   email :
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
        title?.let {
            tv_frag1.text=it
        }

        abv_type2.setOnRightImageListener(View.OnClickListener { toast("点击了图片") })
        abv_type3.setOnRightImageListener(View.OnClickListener { toast("点击了图片") })
        abv_type3.setOnRightTextListener(View.OnClickListener { toast("点击了文字") })
        abv_type4.setOnRightImageListener( View.OnClickListener { toast("点击了图片") })
        abv_type4.setOnRightTextListener(View.OnClickListener { toast("点击了文字") })
    }

    override fun lazyLoad() {

    }
}