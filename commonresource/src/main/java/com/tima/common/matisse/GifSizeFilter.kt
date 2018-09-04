package com.tima.common.matisse

import android.content.Context
import com.tima.common.R
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.IncapableCause
import com.zhihu.matisse.internal.entity.Item
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils
import java.util.HashSet

/**
 * Created by Administrator on 2018/9/4.
 */
class GifSizeFilter : Filter{

    var mMinWidth: Int = 0
    var mMinHeight: Int = 0
    var mMaxSize: Int = 0

    constructor(minWidth: Int, minHeight: Int, maxSizeInBytes: Int){
        mMinWidth = minWidth
        mMinHeight = minHeight
        mMaxSize = maxSizeInBytes
    }

    public override fun constraintTypes(): Set<MimeType> {
        return object : HashSet<MimeType>() {
            init {
                add(MimeType.GIF)
            }
        }
    }

    override fun filter(context: Context, item: Item): IncapableCause? {
        if (!needFiltering(context, item))
            return null

        val size = PhotoMetadataUtils.getBitmapBound(context.contentResolver, item.contentUri)
        return if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
            IncapableCause(IncapableCause.DIALOG, context.getString(R.string.error_gif, mMinWidth,
                    PhotoMetadataUtils.getSizeInMB(mMaxSize.toLong()).toString()))
        } else null
    }
}