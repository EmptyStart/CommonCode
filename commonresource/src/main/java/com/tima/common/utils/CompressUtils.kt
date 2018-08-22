package com.tima.common.utils

import android.annotation.SuppressLint
import android.graphics.*
import java.io.*
import java.lang.Exception

/**
 *  压缩工具
 * Created by Administrator on 2018/8/22/022.
 */

object CompressUtils{


    /**
     * 图片按比例大小压缩方法
     */
    fun scaleCompressImage(filepath: String, bitmap: Bitmap?): Int {
        var state = -1
        var image: Bitmap? = bitmap
        if (image == null) {
            image = onReadImg(filepath)
        }
        if (image == null) return state
        var screenwidth = 720
        var screenheight = 1280
        try {
            var baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var options = 100
            while (baos.toByteArray().size / 1024 > 1024 * 2) {                                     //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset()                                                                        //重置baos即清空baos
                image!!.compress(Bitmap.CompressFormat.JPEG, options, baos)                         //这里压缩options%，把压缩后的数据存放到baos中
                options -= 10                                                                       //每次都减少10
            }
            if (baos.toByteArray().size > 1024 * 1024 * 2)
                return -1

            var isBm = ByteArrayInputStream(baos.toByteArray())
            var newOpts = BitmapFactory.Options()

            /**
             * 开始读入图片，此时把options.inJustDecodeBounds 设回true了
             */
            newOpts.inJustDecodeBounds = true
            BitmapFactory.decodeStream(isBm, null, newOpts)
            newOpts.inJustDecodeBounds = false

            var w = newOpts.outWidth
            var h = newOpts.outHeight

            /**
             * 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
             */
            var hh = screenheight.toFloat()                                                        //这里设置高度
            var ww = screenwidth.toFloat()                                                         //这里设置宽度

            /**
             * 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
             */
            var be = 1                                                                              //be=1表示不缩放
            if (w > h && w > ww) {                                                                  //如果宽度大的话根据宽度固定大小缩放
                be = (newOpts.outWidth / ww).toInt()
            } else if (w < h && h > hh) {                                                           //如果高度高的话根据宽度固定大小缩放
                be = (newOpts.outHeight / hh).toInt()
            }
            if (be <= 0) be = 1
            newOpts.inSampleSize = be                                                               //设置缩放比例

            /**
             *  重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
             */
            isBm = ByteArrayInputStream(baos.toByteArray())
            var mImage = BitmapFactory.decodeStream(isBm, null, newOpts)
            state = compressImage(mImage, filepath)
            try {
                isBm.close()
                baos.close()
                if (image != null && !image.isRecycled) {
                    image.recycle()
                }
                if (mImage != null && !mImage.isRecycled) {
                    mImage.recycle()
                }
                System.gc()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return state                                                                                //压缩好比例大小后再进行质量压缩
    }


    /**
     * 质量压缩方法
     */
    fun compressImage(image: Bitmap, filepath: String): Int {
        var state = -1
        var baos = ByteArrayOutputStream()
        var isBm : ByteArrayInputStream? = null
        var mImage : Bitmap ?= null
        try {
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)                                   //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            var options = 100
            while (baos.toByteArray().size / 1024 > 1024 * 2) {                                     //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset()                                                                        //重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos)                           //这里压缩options%，把压缩后的数据存放到baos中
                options -= 10                                                                       //每次都减少10
            }
            if (baos.toByteArray().size > 1024 * 1024 * 2)
                return state
            isBm = ByteArrayInputStream(baos.toByteArray())                                     //把压缩后的数据baos存放到ByteArrayInputStream中
            mImage = BitmapFactory.decodeStream(isBm, null, null)                              //把ByteArrayInputStream数据生成图片
            state = onSaveImg(filepath, mImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            if (baos != null)
                baos.close()
            if (isBm != null)
                isBm.close()
            if (image != null && !image.isRecycled) {
                image.recycle()
            }
            if (mImage != null && !mImage.isRecycled) {
                mImage.recycle()
            }
            System.gc()
        }
        return state
    }

    /**
     * 照片添加 地址 时间水印效果
     * @param filepath 照片
     * @param datas    地址、时间
     * @return         加了地址、时间的水印图片
     */
    @SuppressLint("WrongConstant")
    fun PhotoWaterMark(datas: Array<String>?, screenwidth: Int, screenheight: Int, filepath: String, Path: String?): Int {
        var state = -1
        if (null != datas && datas.size > 0) {
            val bitmap = onReadImg(filepath)
            val baos = ByteArrayOutputStream()
            val ico = Bitmap.createBitmap(screenwidth, screenheight, Bitmap.Config.RGB_565)         // 建立一个空的BItMap
            try {
                if (null != bitmap) {
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                } else {
                    return state
                }
                var options = 100
                while (baos.toByteArray().size / 1024 > 1024) {                                     //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                    baos.reset()                                                                    //重置baos即清空baos
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, options, baos)                    //这里压缩options%，把压缩后的数据存放到baos中
                    options -= 20                                                                   //每次都减少10
                }

                val textspace: Int
                val canvas = Canvas(ico)                                                            // 初始化画布绘制的图像到icon上
                val photoPaint = Paint()                                                            // 建立画笔
                photoPaint.isDither = true                                                          // 获取跟清晰的图像采样
                photoPaint.isFilterBitmap = true                                                    // 过滤一些
                val src = Rect(0, 0, bitmap!!.getWidth(), bitmap!!.getHeight())                     // 创建一个指定的新矩形的坐标
                val dst = Rect(0, 0, screenwidth, screenheight)                                     // 创建一个指定的新矩形的坐标
                canvas.drawBitmap(bitmap!!, src, dst, photoPaint)                                    // 将photo 缩放或则扩大到  dst使用的填充区photoPaint
                val textPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)            // 设置画笔
                if (datas.size > 0 && null != datas[0] && datas[0].trim { it <= ' ' }.length > 0) {
                    textPaint.textSize = (screenwidth / (datas[0].length + 5)).toFloat()
                    textspace = Math.max(screenwidth, screenheight) / 20
                } else {
                    textPaint.textSize = (screenwidth / 20).toFloat()
                    textspace = Math.max(screenwidth, screenheight) / 20
                }
                textPaint.typeface = Typeface.DEFAULT_BOLD// 采用默认的宽度
                textPaint.color = Color.RED// 采用的颜色
                for (i in datas.indices) {
                    if (null != datas[i]) {
                        canvas.drawText(datas[i], textspace.toFloat(), (textspace * (i + 1)).toFloat(), textPaint)// 绘制上去字，开始未知x,y采用那只笔绘制
                    }
                }
                canvas.save(Canvas.ALL_SAVE_FLAG)
                canvas.restore()
                state = onSaveImg(filepath, ico)
            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                if (ico.isRecycled) {
                    ico.recycle()
                }
                if (bitmap!!.isRecycled()) {
                    bitmap!!.recycle()
                }
                if (baos != null)
                    baos.close()
                System.gc()
            }
        }
        return state
    }


    /**
     * 读取相册照片
     * @param path  照片路径
     * @return      返回照片
     */
    fun onReadImg(path: String): Bitmap? {
        var fis: FileInputStream? = null
        var bitmap: Bitmap? = null
        if (path != null) {
            try {
                var options = BitmapFactory.Options()
                options.inSampleSize = 4
                fis = FileInputStream(path)
                bitmap = BitmapFactory.decodeStream(fis, null, options)
                if (bitmap != null)
                    return bitmap
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                if (fis != null)
                    fis.close()
            }
        }
        return bitmap
    }


    /**
     * 保存图片到本地
     * @param path   保存的地址
     * @param bitmap 保存的图片
     */
    fun onSaveImg(path: String, bitmap: Bitmap): Int {
        var fos: FileOutputStream? = null
        var file = File(path)
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)                           // 把数据写入文件
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return -1
        } finally {
            if (fos != null) {
                fos.flush()
                fos.close()
            }
        }
        return 1
    }

}