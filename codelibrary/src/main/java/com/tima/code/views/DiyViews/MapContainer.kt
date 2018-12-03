package com.tima.code.views.DiyViews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.ScrollView

/**
 * @author : zhijun.li on 2018/9/7
 *   email :
 *
 */
class MapContainer : RelativeLayout{
    private var scrollView: ScrollView?=null
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
            attrs,defStyleAttr)

    fun setScrollView(scrollView: ScrollView){
        this.scrollView=scrollView
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.getAction() == MotionEvent.ACTION_UP) {
            scrollView?.requestDisallowInterceptTouchEvent(false);
        } else {
            scrollView?.requestDisallowInterceptTouchEvent(true);
        }
        return false
    }
}