package com.tima.code.responsebody

/**
 * @author : lzj on 2018/9/8 22:47
 *         # Email
 */
data class LocationBean(val latitude: Double,
                        val longitude : Double,
                        val locAddress : String,
                        val province: String,
                        val city : String,
                        val district : String,
                        val snippet : String
)