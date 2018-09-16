package com.tima.common.base

import com.amap.api.location.AMapLocation
import com.tima.common.utils.FileUtils
import com.tima.common.utils.GsonUtils
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


    var token: String by SpHelper("token", "")

    var hrId: String by SpHelper("hrId", "")


    var partCommit: Double by SpHelper("parttime_commition", 0.1)

    var companyId: String by SpHelper("companyId", "")

    //获取当前角色   1 表示个人  2表示公司  0表示 默认值
    var position by SpHelper("position", 0)

    var aMapLocation : AMapLocation?=null

    //纬度
    var latitude: Double = -1.0
    //经度
    var longitude: Double = -1.0

    var locAddress: String? = null

    val province by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        GsonUtils.getGson.fromJson(FileUtils.readProvince(), ProvinceBody::class.java)
    }

}