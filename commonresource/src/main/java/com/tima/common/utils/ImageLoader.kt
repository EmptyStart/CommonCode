package com.tima.common.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * @author : zhijun.li on 2018/8/14
 *   email : zhijun.li@timanetworks.com
 *
 */
object ImageLoader {

    fun load(context: Context?, url: String?, iv: ImageView?) {
        iv?.let {
            Glide.with(context!!)
                    .load(url)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(iv)
        }
    }
}