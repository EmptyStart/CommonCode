package com.tima.common.base

import com.tima.common.utils.SpHelper


/**
 * @author : zhijun.li on 2018/8/13
 *   email : zhijun.li@timanetworks.com
 *
 */
object Constant {
    //lateinit不支持数据基础类型
//    private var mNumber: Int by Delegates.notNull<Int>()
//    lateinit var baseUrl: String


    val token : String by SpHelper("token","")

    //获取当前角色   1 表示个人  2表示公司  0表示 默认值
    fun getPosition(): Int {
        val set by SpHelper("position", 0)
        return set
    }



    /**
     * cookie
     */
    const val DEFAULT_TIMEOUT :Long=15
    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"

    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
                .map { cookie ->
                    cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                .forEach {
                    it.filterNot { set.contains(it) }.forEach { set.add(it) }
                }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

}