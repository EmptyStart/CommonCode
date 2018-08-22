package com.tima.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.text.TextUtils
import java.io.*

/**
 *  相机工具
 * Created by Administrator on 2018/8/22/022.
 */
object CameraUtils {
    val REQUEST_PICK_PICTURE = 0x1955                                          //相册
    val REQUEST_TAKE_PICTURE = 0x1956                                          //相机
    val REQUEST_EDIT_PICTURE = 0x1957                                          //裁剪后的图

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
        val uri = getRandomUri(activity)
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
                val mCurrentPhotoFile = File(getCacheDownloadDir(), mFileName)
                filepath = mCurrentPhotoFile.absolutePath.trim { it <= ' ' }

                val intent = Intent()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //获取文件提供者 跟manifest保持一致
                    val photoURI = FileProvider.getUriForFile(activity, "com.zksr.newywy.jjy.fileprovider", mCurrentPhotoFile)
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
    fun getPath(uri: Uri, activity: Activity): String {
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
                val file = File(getCacheDownloadDir(),
                        System.currentTimeMillis().toString() + ".png")
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

    /**
     * 获取随机路径
     * @return
     */
    fun getRandomUri(context: Context): Uri {
        val mFileName = System.currentTimeMillis().toString() + ".jpg"
        val mCurrentPhotoFile = File(getCacheDownloadDir(), mFileName)
        val photoURI = FileProvider.getUriForFile(context, "com.tima.mainmodul.lj.jjy.fileprovider", mCurrentPhotoFile)
        return photoURI
    }

    /**
     * 获取缓存目录
     */
    fun getCacheDownloadDir(): String {
        var cacheDownloadDir = ""
        val downloadRootPath = File.separator + "download" + File.separator
        val cacheDownloadPath = downloadRootPath + "cache" + File.separator
        val root = Environment.getExternalStorageDirectory()
        val cacheDownloadDirFile = File(root.absolutePath + cacheDownloadPath)
        if (!cacheDownloadDirFile.exists()) {
            cacheDownloadDirFile.mkdirs()
        }
        cacheDownloadDir = cacheDownloadDirFile.path
        return cacheDownloadDir
    }


}