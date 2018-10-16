package com.tima.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.util.Log
import com.tima.common.R
import com.tima.common.matisse.GifSizeFilter
import com.tima.common.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.listener.OnCheckedListener
import com.zhihu.matisse.listener.OnSelectedListener
import java.io.*

/**
 *  相机工具
 * Created by Administrator on 2018/8/22/022.
 */
object CameraUtils {
    val REQUEST_PICK_PICTURE = 0x1955                                          //相册
    val REQUEST_TAKE_PICTURE = 0x1956                                          //相机
    val REQUEST_CODE_CHOOSE = 0x1957                                           //Matisse - 调用相机、相册
    val REQUEST_EDIT_PICTURE = 0x1957                                          //裁剪后的图
    val AUTHORITY = "com.tima.mainmodul.lj.jjy.fileprovider"                   //过滤存储 路径

    val HEAD_PICTION = 0x2001
    val IMAGE_21 = 0x2002
    val IMAGE_22 = 0x2003
    val IMAGE_23 = 0x2004
    val IMAGE_LOGO = 0x2005
    /**
     * 调用 Matisse 相机并且相册
     */
    fun matisseCameraOrAlbum(activity: Activity){
        Matisse.from(activity)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        CaptureStrategy(true, AUTHORITY))
                .maxSelectable(9)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(Glide4Engine())    // for glide-V4
                .setOnSelectedListener(OnSelectedListener { uriList, pathList ->
                    LogUtils.e("CameraUtils", "onSelected: pathList=" + pathList)
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .setOnCheckedListener(OnCheckedListener { isChecked ->
                    LogUtils.i("CameraUtils", "onCheck: isChecked=" + isChecked)
                })
                .forResult(REQUEST_CODE_CHOOSE)
    }
    /**
     * 调用 Matisse 相机并且相册  可以传入自定义请求Code
     */
    fun matisseCameraOrAlbum(activity: Activity,requestCode: Int){
        if (ActivityCompat.checkSelfPermission(activity!!,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), 1)
        } else {
            Matisse.from(activity)
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(
                            CaptureStrategy(true, AUTHORITY))
                    .maxSelectable(1)
                    .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(Glide4Engine())    // for glide-V4
                    .setOnSelectedListener(OnSelectedListener { uriList, pathList ->
                        LogUtils.e("CameraUtils", "onSelected: pathList=" + pathList)
                    })
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .setOnCheckedListener(OnCheckedListener { isChecked ->
                        LogUtils.i("CameraUtils", "onCheck: isChecked=" + isChecked)
                    })
                    .forResult(requestCode)
        }
    }

    /**
     * 调用 Matisse 相册
     */
    fun matisseAlbum(activity: Activity){
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(9)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE)
    }

    /**
     * 调用相册
     * @param activity
     */
    fun onCallAlbum(activity: Activity) : Boolean{
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        try {
            activity.startActivityForResult(intent, REQUEST_PICK_PICTURE)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * 调用相机并裁剪
     * @return
     */
    fun onCutCamera(activity: Activity): Uri {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)  //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        val uri = FileUtils.getRandomUri(activity)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        activity.startActivityForResult(intent, REQUEST_TAKE_PICTURE)
        return uri
    }


    /**
     * 调用相机
     */
    fun onCallCamera(activity: Activity?): String {
        var filepath: String = ""
        try {
            if (ActivityCompat.checkSelfPermission(activity!!,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), 1)
            } else {
                val mFileName = System.currentTimeMillis().toString() + ".jpg"
                val mCurrentPhotoFile = File(FileUtils.getCacheDownloadDir(), mFileName)
                filepath = mCurrentPhotoFile.absolutePath.trim { it <= ' ' }

                val intent = Intent()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //获取文件提供者 跟manifest保持一致
                    val photoURI = FileProvider.getUriForFile(activity, AUTHORITY, mCurrentPhotoFile)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)//表示对目标应用临时授权该uri所代表的文件
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)////设置拍照后输出url
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile))
                }
                intent.action = MediaStore.ACTION_IMAGE_CAPTURE//设置action为拍照
                intent.putExtra("return-data", true)
                try {
                    activity?.startActivityForResult(intent, REQUEST_TAKE_PICTURE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return filepath
    }

    /**
     * TODO 调用系统的图片裁剪
     * @param imageUri            与裁剪图片本地Uri
     * *
     * @param imageCropedCacheUri 裁剪后图片本地缓存Uri
     * *
     * @param aspectX             x轴裁剪比例
     * *
     * @param aspectY             y轴裁剪比例
     * *
     * @param outputX             输出的宽
     * *
     * @param outputY             输出的高
     */
    fun cropImage(imageUri: Uri, imageCropedCacheUri: Uri, aspectX: Int, aspectY: Int, outputX: Int, outputY: Int): Intent {
        val intent = Intent("com.android.camera.action.CROP")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {                                       //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.setDataAndType(imageUri, "image/*")
        //TODO 是否裁剪
        intent.putExtra("crop", "true")
        //TODO 设置xy的裁剪比例
        intent.putExtra("aspectX", aspectX)
        intent.putExtra("aspectY", aspectY)
        //TODO 设置输出的宽高
        intent.putExtra("outputX", outputX)
        intent.putExtra("outputY", outputY)
        //TODO 是否缩放
        intent.putExtra("scale", false)
        //TODO 输入预裁剪图片的Uri，指定以后，可以通过这个Uri获得图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropedCacheUri)
        //TODO 是否返回图片数据可以不用，直接用Uri就可以
        intent.putExtra("return-data", false)
        //TODO 设置输出图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        //TODO 是否关闭面部识别
        intent.putExtra("noFaceDetection", true)
        return intent
    }

    /**
     * 根据uri获取path
     */
    @SuppressLint("NewApi")
    fun getPath(uri: Uri, activity: Activity): String? {
        var path: String?
        val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
        val c = activity.contentResolver.query(uri, filePathColumns, null, null, null)
        c!!.moveToFirst()
        val columnIndex = c.getColumnIndex(filePathColumns[0])
        path = c.getString(columnIndex)
        c.close()

        if (TextUtils.isEmpty(path)) {                  // 如果没有sd卡时放到本地存储中
            var inputStream: InputStream? = null
            var bos: BufferedOutputStream? = null
            try {
                inputStream = activity.contentResolver.openInputStream(uri)
                val file = File(FileUtils.getCacheDownloadDir(), System.currentTimeMillis().toString() + ".png")
                path = file.absolutePath
                bos = BufferedOutputStream(FileOutputStream(file))
                val bytes = ByteArray(512)
                var len : Int = inputStream.read(bytes)
                while (len != 0){
                    len = inputStream.read(bytes)
                    bos.write(bytes, 0, len)
                    bos.flush()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                path = null
            } catch (e: IOException) {
                e.printStackTrace()
                path = null
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close()
                    }
                    if (bos != null) {
                        bos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return path
    }

}