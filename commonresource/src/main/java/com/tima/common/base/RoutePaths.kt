package com.tima.common.base

/**
 * @author : zhijun.li on 2018/6/25
 *   email : zhijun.li@timanetworks.com
 *   放置跳转地址
 */
object RoutePaths{
    const val group1 = "all"
    /**
     * activitys
     */
    //欢迎界面
    const val welcome="/login/welcomepage"
    //登录界面
    const val login="/login/loginpage"
    //注册/选择个人注册方式
    const val registerSelect="/register/select"
    //注册/个人注册
    const val registerPrivate="/register/private"
    //注册/公司注册
    const val registerCompany="/register/company"
    //注册/地图选择
    const val registerMap="/register/map"
    //注册/创建公司
    const val createcompany="/register/createcompany"
    //主界面
    const val mainpage="/mainpage/mainpage"
    //发布/全职
    const val releasefulltime="/release/fulltime"
    //发布/兼职
    const val releaseparttime="/release/parttime"



    /**
     * fragments
     */
    const val fragmentutils="/utils/fragment"


    //发布
    const val releasefragment="/mainpage/releasefragment"
    //管理
    const val managefragment="/mainpage/managefragment"
    //我的
    const val minefragment="/mainpage/minefragment"
    //消息
    const val newsfragment="/mainpage/newsfragment"

    //消息/沟通
    const val communicatefragment="/mainpage/news/communicatefragment"
    //消息/互动
    const val interactionfragment="/mainpage/news/interactionfragment"
    //管理/全职
    const val manageftfragment="/mainpage/manage/fulltimefragment"
    //管理/兼职
    const val manageptfragment="/mainpage/manage/parttimefragment"



}