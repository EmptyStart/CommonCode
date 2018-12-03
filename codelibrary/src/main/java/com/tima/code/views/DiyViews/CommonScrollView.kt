package com.tima.code.views.DiyViews

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * @author : zhijun.li on 2018/9/7
 *   email :
 *
 */
class CommonScrollView : ScrollView {
    private var mOnScrollToBottomListener: onScrollToBottomListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        // 滑动的距离加上本身的高度与子View的高度对比
        if (t + getHeight() >= getChildAt(0).getMeasuredHeight()) {
            // ScrollView滑动到底部
            mOnScrollToBottomListener?.onScrollToBottom();
        } else {
            mOnScrollToBottomListener?.onNotScrollToBottom();
        }
    }

    fun addOnScrollToBottomListener(listener: onScrollToBottomListener) {
        mOnScrollToBottomListener=listener
    }


}
 interface onScrollToBottomListener {
    fun onScrollToBottom()
    fun onNotScrollToBottom()
}