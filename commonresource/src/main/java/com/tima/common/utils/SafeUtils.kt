package com.tima.common.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @author : zhijun.li on 2018/7/1
 *   email : zhijun.li@timanetworks.com
 *
 */
object SafeUtils{
    //加密
     fun encrypt(input: String, password: String): String {
        var encrypt = ""
        try {
            //创建cipher对象
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            //初始化cipher
            //通过秘钥工厂生产秘钥
            val keySpec = SecretKeySpec(password.toByteArray(), "AES")
            val iv = IvParameterSpec("0123456789acegik".toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
            //加密、解密
            val doFinal = cipher.doFinal(input.toByteArray())
            val encode = Base64.encode(doFinal, Base64.DEFAULT)
            encrypt = String(encode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return encrypt
    }

    //解密
     fun decrypt(input: String, password: String): String {
        var decrypt = ""
        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val keySpec = SecretKeySpec(password.toByteArray(), "AES")
            val iv = IvParameterSpec("0123456789acegik".toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
            val doFinal = cipher.doFinal(Base64.decode(input, Base64.DEFAULT))
            decrypt = String(doFinal)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return decrypt
    }
}

