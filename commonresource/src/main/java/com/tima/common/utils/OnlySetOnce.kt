package com.tima.common.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author : zhijun.li on 2018/8/13
 *   email : zhijun.li@timanetworks.com
 *
 */
class OnlySetOnce {
    private class OnlyOneSetValue<T>() : ReadWriteProperty<Any?, T> {
        var value: T? = null

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value
            else
                throw IllegalStateException("Property ${property.name} can be setted only once！")
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value
                    ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
        }
    }

    companion object {
        fun <T> OnlySetOnceExt(): ReadWriteProperty<Any?, T> = OnlyOneSetValue()
    }
}