package com.tima.common.base

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.zhy.autolayout.utils.AutoUtils

/**
 * @author : zhijun.li on 2018/7/1
 *   email : zhijun.li@timanetworks.com
 *
 */
class HomeViewHolder : BaseViewHolder{
    constructor(view :View) : super(view){
        AutoUtils.auto(view)
    }
}