package com.xx.code.timaconstracts

import com.xx.common.base.IBaseViewModel
import com.xx.common.base.IDataListener

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