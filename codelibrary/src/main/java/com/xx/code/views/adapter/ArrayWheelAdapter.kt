package com.xx.code.views.adapter

import com.contrarywind.adapter.WheelAdapter

/**
 * @author : lzj on 2018/9/6 23:17
 *         # Email
 */
class ArrayWheelAdapter<T>(list: ArrayList<T>) : WheelAdapter<T> {
    val items = list
    override fun indexOf(o: T): Int {
        return items.indexOf(o)
    }

    override fun getItemsCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): T {
        return items.get(index)
    }
}