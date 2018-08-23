package com.tima.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.tima.common.R

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
 *
 */
class ActionBarView : LinearLayout {
    var mTitle: String? = null
    var mTitle_right: String? =null
    var mType: Int? = 1000

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ActionBarView)
        mTitle = typeArray?.getString(R.styleable.ActionBarView_mode_title)
        mTitle_right=typeArray?.getString(R.styleable.ActionBarView_mode_right_title)
        mType=typeArray?.getInt(R.styleable.ActionBarView_mode_right,1000)
        typeArray?.recycle()

        initView(context)
    }

    private fun initView(context: Context?) {
//        LayoutInflater.from(context).inflate(R.la)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}