package com.tima.common.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import java.io.*
import android.R.attr.path
import android.provider.MediaStore
import com.tima.common.base.App


/**
 * @author : zhijun.li on 2018/8/20
 *   email : zhijun.li@timanetworks.com
 *
 */
object FileUtils {

    val PICTURE_DIR = "pictures/"
    val CAMERA_DIR = "Camera/"
    val VOICE_DIR = "voices/"
    val VERSION_DIR = "version/"
    val THUMB_DIR = "ThumbDir/"
    val FILEPATH = "android/mainmodul/"

    /**
     * 获取文件路径
     * @param fileName sd卡相对路径，如“/”表示根目录
     * *
     * @return
     */
    fun getDirectory(fileName: String): String {
        //return  getCacheDownloadDir() + "/" + FILEPATH + fileName
        return getCacheDownloadDir() + "/" + fileName
    }

    /**
     * 获取语音本地路径
     * @return /sdcard/ywk/voices/
     */
    fun getVoiceDirectory(): String {
        return getDirectory(VOICE_DIR)
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


    /**
     * 获取随机路径
     * @return
     */
    fun getRandomUri(context: Context): Uri {
        val mFileName = System.currentTimeMillis().toString() + ".jpg"
        val mCurrentPhotoFile = File(FileUtils.getCacheDownloadDir(), mFileName)
        val photoURI = FileProvider.getUriForFile(context, CameraUtils.AUTHORITY, mCurrentPhotoFile)
        return photoURI
    }

    /**
     * 取SD卡路径
     * @return
     */
    private fun getSDPath(): String {
        var sdDir: File? = null
        val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory() // 获取根目录
        }
        if (sdDir != null) {
            return sdDir.toString()
        } else {
            return ""
        }
    }

    /**
     * //检查sd卡是否存在
     */
    fun isSdCardExist(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 获取根目录
     */
    fun getRootDir(): String {

        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Environment.getExternalStorageDirectory()
                    .absolutePath
        } else {
            ""
        }

    }

    /**
     * 可创建多个文件夹
     * dirPath 文件路径
     */
    fun mkDir(dirPath: String) {

        val dirArray = dirPath.split("/".toRegex())
        var pathTemp = ""
        for (i in 1 until dirArray.size) {
            pathTemp = "$pathTemp/${dirArray[i]}"
            val newF = File("${dirArray[0]}$pathTemp")
            if (!newF.exists()) {
                val cheatDir: Boolean = newF.mkdir()
                println(cheatDir)
            }
        }

    }

    /**
     * 创建文件
     * dirpath 文件目录
     * fileName 文件名称
     */
    fun creatFile(dirPath: String = getRootDir(), fileName: String) {
        val file = File("$dirPath/$fileName")
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    /**
     * 创建文件
     * filePath 文件路径
     */
    fun creatFile(filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    /**
     * 创建文件
     * filePath 文件路径
     */
    fun creatFile(filePath: File) {
        if (!filePath.exists()) {
            filePath.createNewFile()
        }
    }

    /**
     * 删除文件
     *
     * dirpath 文件目录
     * fileName 文件名称
     */
    fun delFile(dirpath: String = getRootDir(), fileName: String): Boolean {
        val file = File("$dirpath/$fileName")
        if (file.checkFile()) {
            return false
        }
        return file.delete()
    }

    /**
     *  删除文件
     *  filepath 文件路径
     */
    fun delFile(filepath: File): Boolean {
        if (filepath.checkFile()) {
            return false
        }
        return filepath.delete()
    }

    /**
     *  删除文件
     *  filepath 文件路径
     */
    fun delFile(filepath: String): Boolean {
        val file = File(filepath)
        if (file.checkFile()) {
            return false
        }
        return file.delete()
    }


    /**
     * 删除文件夹
     * dirPath 文件路径
     */
    fun delDir(dirpath: String) {
        val dir = File(dirpath)
        deleteDirWihtFile(dir)
    }

    fun deleteDirWihtFile(dir: File?) {
        if (dir!!.checkFile())
            return
        for (file in dir.listFiles()) {
            if (file.isFile)
                file.delete() // 删除所有文件
            else if (file.isDirectory)
                deleteDirWihtFile(file) // 递规的方式删除文件夹
        }
        dir.delete()// 删除目录本身
    }

    private fun File.checkFile(): Boolean {
        return this == null || !this.exists() || !this.isDirectory
    }

    /**
     * 修改SD卡上的文件或目录名
     * oldFilePath 旧文件或文件夹路径
     * newFilePath 新文件或文件夹路径
     */
    fun renameFile(oldFilePath: String, newFilePath: String): Boolean {
        val oldFile = File(oldFilePath)
        val newFile = File(newFilePath)
        return oldFile.renameTo(newFile)
    }


    /**
     * 拷贝一个文件
     * srcFile源文件
     * destFile目标文件
     */
    @Throws(IOException::class)
    fun copyFileTo(srcFile: File, destFile: File): Boolean {
        return if (srcFile.isDirectory || destFile.isDirectory) {
            false
        } else {
            val fis = FileInputStream(srcFile)
            val fos = FileOutputStream(destFile)
            fis.copyTo(fos)
            fos.flush()
            closeIO(fos, fis)
            true
        }
    }

    /**
     *拷贝目录下的所有文件到指定目录
     * srcDir 原目录
     * destDir 目标目录
     */
    fun copyFilesTo(srcDir: File, destDir: File): Boolean {
        if (!srcDir.isDirectory || !destDir.isDirectory) return false// 判断是否是目录
        if (!destDir.exists()) return false// 判断目标目录是否存在
        val srcFiles = srcDir.listFiles()
        srcFiles.forEach {
            if (it.isFile) {
                val destFile = File("${destDir.path}/${it.name}")
                copyFileTo(it, destFile)
            } else {
                val theDestDir = File("${destDir.path}/${it.name}")
                copyFilesTo(it, theDestDir)
            }
        }

        return true
    }

    /**
     * 移动一个文件
     */
    fun moveFileTo(srcFile: File, destFile: File): Boolean {
        if (srcFile.isDirectory || destFile.isDirectory) return false
        val iscopy = copyFileTo(srcFile, destFile)
        return if (!iscopy) {
            false
        } else {
            delFile(srcFile)
            true
        }

    }

    /**
     * 移动目录下的所有文件到指定目录
     * srcDir 原路径
     * destDir 目标路径
     */
    @Throws(IOException::class)
    fun moveFilesTo(srcDir: File, destDir: File): Boolean {
        if (!srcDir.isDirectory or !destDir.isDirectory) {
            return false
        } else {
            val srcDirFiles = srcDir.listFiles()
            srcDirFiles.forEach {
                if (it.isFile) {
                    val oneDestFile = File("${destDir.path}/${it.name}")
                    moveFileTo(it, oneDestFile)
                    delFile(it)
                } else {
                    val oneDestFile = File(destDir.path + "//"
                            + it.name)
                    moveFilesTo(it, oneDestFile)
                    delDir(it.absolutePath)
                }

            }
            return true
        }

    }

    /**
     * 文件转byte数组
     * file 文件路径
     */
    @Throws(IOException::class)
    fun file2byte(file: File): ByteArray? {
        var bytes: ByteArray? = null
        if (file != null) {
            val inp = FileInputStream(file)
            val length = file.length() as Int
            if (length > Integer.MAX_VALUE) {// 当文件的长度超过了int的最大值
                inp.close()
                return null
            }
            bytes = inp.readBytes(length)
            closeIO(inp)
        }
        return bytes
    }

    /**
     * 文件读取
     * filePath 文件路径
     */
    fun readFile(filePath: File): String? {
        if (!filePath.isFile) {
            return null
        } else {
            return filePath.readText()
        }
    }

    /**
     * 文件读取
     * strPath 文件路径
     */
    fun readFile(strPath: String): String? {
        return readFile(File(strPath))
    }

    /**
     * InputStream 转字符串
     */
    fun readInp(inp: InputStream): String? {
        val bytes: ByteArray = inp.readBytes()
        return String(bytes)
    }

    /**
     * BufferedReader 转字符串
     */
    fun readBuff(buff: BufferedReader): String? {
        return buff.readText()
    }

    /**
     * 写入数据
     */
    fun writeText(filePath: File, content: String) {
        creatFile(filePath)
        filePath.writeText(content)
    }

    /**
     * 追加数据
     */
    fun appendText(filePath: File, content: String) {
        creatFile(filePath)
        filePath.appendText(content)
    }

    /**
     * 追加数据
     */
    fun appendBytes(filePath: File, array: ByteArray) {
        creatFile(filePath)
        filePath.appendBytes(array)
    }

    /**
     * 获取文件大小
     */
    fun getLeng(filePath: File): Long {
        return if (!filePath.exists()) {
            -1
        } else {
            filePath.length()
        }
    }

    /**
     * 按时间排序
     */
    fun sortByTime(filePath: File): Array<File>? {
        if (!filePath.exists()) {
            return null
        }
        val files: Array<File> = filePath.listFiles()
        if (files.isEmpty()) {
            return null
        }
        files.sortBy { it.lastModified() }
        files.reverse()
        return files

    }

    /**
     * 关闭IO
     * @param closeables closeables
     */
    fun closeIO(vararg closeables: Closeable) {
        if (closeables == null) return
        closeables
                .filterNotNull()
                .forEach {
                    try {
                        it!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
    }

    fun uriToFile(uri: Uri?): File? {
        var path = ""
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = App.app.getContentResolver().query(uri, proj, null, null, null)
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            path = cursor.getString(columnIndex)
        }
        cursor.close()
        if (path.isEmpty()) {
            return null
        } else {
            return File(path)
        }
    }

    fun picTailName(picName: String?): String? {
        picName ?: return null
        val length = picName.length
        var subSequence = picName.subSequence(length - 4, length)
        if (subSequence.contains(".")) {
            val length1 = subSequence.length
            subSequence = subSequence.subSequence(1, length1)
        }
        return subSequence.toString()
    }

    fun readAssetFiles(fileName: String): String? {
        val assets = App.app.assets
        val stringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(assets.open(fileName), "UTF-8")).run {
            var line: String? = ""
            do {
                line = readLine()
                if (line.isNullOrEmpty()) {
                    close()
                    break
                } else {
                    stringBuilder.append(line)
                }
            } while (true)
            assets.close()
            return stringBuilder.toString()
        }

    }

    /**
     * 获取省市区数据
     */
    fun readProvince(): String? {
        var province : String? =""
        try {
            province = readAssetFiles("province.json")
            province="{\n\"districts\":"+province+"}"
        }catch (e: Exception){
            e.printStackTrace()
        }
        return  province
    }
}