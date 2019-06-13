package com.xx.common.views

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.xx.common.R
import com.zhy.autolayout.AutoLinearLayout
import org.jetbrains.anko.find

/**
 * @author : zhijun.li on 2018/8/23
 *   email :
 *
 */
class ActionBarView : AutoLinearLayout {
    var mTitle: String? = null
    var mTitle_right: String? = null
    var mType: Int? = 1000
    var mRightImage: Int? = 0

    var tv_title: TextView? = null
    var ll_right: LinearLayout? = null
    var iv_actionbar_cancle: ImageView? = null
    var tv_actionbar_line: View? = null
    var tv_actionbar_right_title: TextView? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ActionBarView)
        mTitle = typeArray?.getString(R.styleable.ActionBarView_mode_title)
        mTitle_right = typeArray?.getString(R.styleable.ActionBarView_mode_right_title)
        mType = typeArray?.getInt(R.styleable.ActionBarView_mode_type, 1000)
        mRightImage = typeArray?.getResourceId(R.styleable.ActionBarView_mode_right_image, 0)
        typeArray?.recycle()

        initView(context)
    }

    private fun initView(context: Context?) {
        val view = LayoutInflater.from(context).inflate(R.layout.common_actionbar_view, this)
        tv_title=view.find(R.id.tv_actionbar_title)
        ll_right=view.find(R.id.ll_right_view)
        iv_actionbar_cancle=view.find(R.id.iv_actionbar_cancle)
        tv_actionbar_line=view.find(R.id.tv_actionbar_line)
        tv_actionbar_right_title=view.find(R.id.tv_actionbar_right_title)


        setTitle(mTitle)

        when(mType){
            1001->{
                ll_right?.visibility= View.VISIBLE
                iv_actionbar_cancle?.visibility=View.VISIBLE
                setRightImage(mRightImage)
            }
            1002->{
                ll_right?.visibility= View.VISIBLE
                iv_actionbar_cancle?.visibility=View.VISIBLE
                tv_actionbar_line?.visibility=View.VISIBLE
                tv_actionbar_right_title?.visibility=View.VISIBLE
                setRightImage(mRightImage)
                setRightTitle(mTitle_right)
            }
        }
    }
    fun setTitle(title: String?){
        title?.let {
            tv_title?.text=it
        }
    }

    fun setRightImage(@DrawableRes image:Int?){
        if (image!=0) {
            image?.let {
                iv_actionbar_cancle?.setImageResource(image)
            }
        }
    }

    fun setRightTitle(rightTitle : String?){
        rightTitle?.let {
            tv_actionbar_right_title?.text=rightTitle
        }
    }

    fun setOnRightImageListener(listener: OnClickListener){
        iv_actionbar_cancle?.setOnClickListener(listener)
    }
    fun setOnRightTextListener(listener: OnClickListener){
        tv_actionbar_right_title?.setOnClickListener(listener)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}