package com.tima.common.https

/**
 * @author : zhijun.li on 2018/8/31
 *   email :
 *
 */
object CommonUrls {
    /**
     *     登录验证
     */
    const val loginVerify="login/verify/"
    /**
     * login
     */
    const val login="login/"
 /**
     * logout
     */
    const val logout="logout/"

    /**
     * hr info 需拼接{id}/
     */
    const val hrInfo="hr/"

    /**
     * company info/
     */
    const val companyInfo="company/"
    /**
     * 发布职业
     */
    const val releaseTime="position/"
   /**
    * 兼职发布  余额不足的情况
    */
    const val replayTime="position/repay/"

    /**
     * 配置信息查询
     */
    const val configInfo="config/"
    /**
     *职位类型查询
     */
    const val careerType="careertype/"

    /**
     * 提交图片
     */
    const val upPhoto="company/photo/"


    const val manageFull="position/"

    const val fund="fund/"

    const val fundDetail = "fund/detail/"

    const val fundPay = "fund/pay/"

    const val wallet = "wallet/"

    const val apply = "apply/"

    const val applyBatch = "apply/batch/"

    const val withdraw = "withdraw/"

    const val alipay = "alipay/prepay/"
}