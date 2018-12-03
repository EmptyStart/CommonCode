package com.tima.common.base

/**
 * @author : zhijun.li on 2018/9/6
 *   email :
 *
 */


data class ProvinceBody(
    val districts: List<District>
)

data class District(
    val name: String,
    val city: List<City>
)

data class City(
    val name: String,
    val area: List<String>
)