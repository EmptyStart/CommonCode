package com.tima.code.timaconstracts

import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IDataListener

/**
 * @author : zhijun.li on 2018/9/10
 *   email :
 *
 */
interface IConfigInfoViewModel : IBaseViewModel{
    /**
     * 配置信息查询
     */
    fun addConfigInfo(listener: IDataListener)

    /**
     * 职位类型
     */
    fun addCareertype(listener: IDataListener)
}