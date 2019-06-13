//package com.xx.mainmodul
//
//import android.content.Context
//import android.widget.ImageView
//import com.alibaba.android.arouter.facade.annotation.Route
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
//import com.xx.common.base.IGlideLoaderConfig
//import com.xx.common.base.RoutePaths
//
///**
// * @author : zhijun.li on 2018/10/17
// *   email :
// *
// */
//@Route(path = RoutePaths.glideService)
//class GlideLoaderService : IGlideLoaderConfig {
//    var context: Context? = null
//    override fun init(context: Context?) {
//        this.context = context
//    }
//
//    override fun load(url: String?, iv: ImageView) {
//        context?.let {
//            GlideApp
//                    .with(it)
//                    .load(url)
//                    .placeholder(R.mipmap.code_register_addphoto)
//                    .error(R.mipmap.code_register_addphoto)
//                    .fitCenter()
//                    .transition(DrawableTransitionOptions().crossFade())
//                    .into(iv)
//        }
//
//    }
//
//}