package com.xx.common.base

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.zhy.autolayout.utils.AutoUtils

/**
 * @author : zhijun.li on 2018/7/1
 *   email :
 *
 */
class HomeViewHolder : BaseViewHolder{
    constructor(view :View) : super(view){
        AutoUtils.auto(view)
    }
}