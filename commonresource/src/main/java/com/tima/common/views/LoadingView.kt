package com.tima.common.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import com.tima.common.R


/**
 * @author : zhijun.li on 2018/9/20
 *   email :
 *
 */
class LoadingView(context: Context) : Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去掉默认的title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉白色边角 我的小米手机在xml里设置 android:background="@android:color/transparent"居然不生效
        //所以在代码里设置，不知道是不是小米手机的原因
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.common_loading)
        val linearLayout = this.findViewById(R.id.LinearLayout) as LinearLayout
        linearLayout.background.alpha = 210
    }
}