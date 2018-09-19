package com.tima.code.ResponseBody

import com.google.gson.annotations.SerializedName
import com.tima.common.https.ApiException
import okhttp3.MediaType
import okio.BufferedSource

/**
 * @author : zhijun.li on 2018/9/3
 *   email : zhijun.li@timanetworks.com
 *
 */

data class LoginResponseBody(
    val hr: Hr,
    val company: String,
    val token: String,
    val img_base: String,
    val huanxin_password: String,
    val parttime_commition: Double
)

data class Hr(
    val id: Int,
    val company: Company,
    val created_on: String,
    val mobile: String,
    val name: String,
    val nick_name: Any,
    val password: Any,
    val avatar: Any,
    val type: String,
    val gender: String,
    val address: String,
    val province: String,
    val city: String,
    val region: String,
    val email: Any,
    val birthday: Any,
    val rating: Any,
    val status: String,
    val register_ip: Any,
    val last_login_ip: String,
    val last_login_on: Any,
    val last_login_device: Any,
    val tel: Any,
    val img1: Any,
    val img2: Any,
    val img3: Any,
    val id_card: Any,
    val id_verified: String,
    val push_id: Any,
    val im_id: Any
)

data class Company(
    val id: Int,
    val full_name: String,
    val short_name: Any,
    val logo: String,
    val type: String,
    val province: String,
    val city: String,
    val region: String,
    val verify: String,
    val status: String,
    val priority: Any,
    val tags: Any,
    val created_on: String,
    val fulltime_amt: String,
    val fulltime_window: Int,
    val fulltime_unit: String,
    val margin: String
)

data class RegisterValidResponseBody(
        val detail: String
)

/**
 * 职位详情
 */
data class Position(
        val id: Int,
        val created_no: String,                                          //创建时间
        var name : String,                                               //职位名称
        var province : String,                                           //省份
        var city : String,                                               //市
        var region : String,                                             //区
        var address : String,                                            //工作地址
        var longitude : Any,                                             //经度
        var latitude : Any,                                              //纬度
        var type : String,                                               //工作性质         0 全职，1兼职
        var salary_type : String,                                        //薪水类型
        var salary_begin : String,                                       //起薪
        var salary_end : String,                                         //满新
        var salary_unit : String,                                        //薪水单位         0 次，1 小时，2 天，3 月，4 年
        var salary_qty : Int,                                            //薪水数量
        var exp_year_beg : Int,                                          //经验要求起始年
        var exp_year_end : Int,                                          //经验要求结束年
        var on_site_time : String,                                       //到场时间
        var education : Int,                                             //要求最低学历    0 不限，1 初中及以下，2 中专/中技，3 高中，4 大专，5 本科，6 硕士，7 博士
        var status : String,                                             //状态            1 正常 2 禁用 3 删除 4 需充值
        //00 申请 01 HR同意 02HR拒绝 10 到场 11HR到场确认 12HR到场未确认 20 Offer发放 21入职确认 22入职拒绝 30 入职完成 31离职 35完成兼职 36中途离场 90 撤销
        var verify : String,                                             //审核状态        0 审核中，1 审核成功，2 审核失败，3 下架
        var priority : Int,                                              //优先级
        var descriptions : String,                                       //职位描述
        var remark : String,                                             //备注
        var interview_day : String,                                      //面试星期        星期几可以面试？如1,2,3,4,5,6
        var interview_time : String,                                     //面试时间        上午面试时间可以面试时间列表，小时为单位 如：7,9,10,16,17
        var interview_from : String,                                     //面试起始日期
        var interview_to : String,                                       //面试结束日期
        var qty : Int,                                                   //需求人数
        var qty_var : Int,                                               //允许偏差人数
        var skill_set : String,                                          //技能
        var company : String,                                            //公司--------文档Int 懵逼
        var career_type : Any,                                           //职位类型                 {"id": 3,"name": "前端开发","status": "1","level": 2,"parent": 1}
        var hr : Int                                                     //Hr 人事
)

/**
 * 证书
 */
data class Certificate(
        val id: Int,
        var created_on: String,                                          //创建时间
        var get_date : String,                                           //获取时间
        var name : String,                                               //证书名称
        var user : String,                                               //用户  ----狗屎 不知道为何文档 Int类型
        var cv : String                                                  //简历
)

/**
 * 经验
 */
data class Experience(
        val id: Int,
        var created_on : String,                                         //创建时间
        var user : Any,                                                  //用户  ----狗屎 不知道为何文档 Int类型
        var cv : Any,                                                    //简历
        var start : String,                                              //开始时间
        var end : String,                                                //结束时间
        var company : String,                                            //公司名称
        var title : String,                                              //职位
        var desc : String,                                               //工作描述
        var achievement : String                                         //工作业绩
)

/**
 * 学历
 */
data class Education(
        val id: Int,
        var created_on : String,                                         //创建时间
        var user : Any,                                                  //用户  ----狗屎 不知道为何文档 Int类型
        var cv : Any,                                                    //简历
        var school : String,                                             //学校
        var graduated_on : String,                                       //毕业时间
        var diploma : String,                                            //学位
        var major : String                                               //专业
)

/**
 * 个人信息
 */
data class User(
        val id: Int,
        val certificates: ArrayList<Certificate>,                        //证书
        var experiences : ArrayList<Experience>,                         //经验
        var educations : ArrayList<Education>,                           //学历
        var created_on : String,                                         //创建时间
        var mobile : String,                                             //帐号
        var name : String,                                               //姓名
        var nick_name : String,                                          //昵称
        var password : String,                                           //密码
        var avatar : Any,                                                //头像
        var type : Any,                                                  //类型
        var gender : String,                                             //性别
        var address : String,                                            //住址
        var province : String,                                           //省份
        var city : String,                                               //市
        var region : String,                                             //区
        var email : String,                                              //邮箱
        var birthyear : Any,                                             //出生年份
        var rating : Any,                                                //等级
        var status : String,                                             //状态
        var cv_status : Any,                                             //简历状态
        var register_ip : Any,                                           //注册IP
        var last_login_ip : String,                                      //最近登录IP
        var last_login_on : Any,                                         //最近登录时间
        var last_login_device : Any,                                     //最近登录设备
        var tel : String,                                                //电话
        var top_diploma : Any,                                           //最高学历
        var work_years : Any,                                            //工作年份
        var img1 : String,                                               //图片1
        var img2 : String,                                               //图片2
        var img3 : String,                                               //图片3
        var id_card : String,                                            //身份证
        var id_verified : Any,                                           //实名认证
        var ref_code : String,                                           //推广码
        var push_id : String,                                            //推送设备ID
        var im_id : Any                                                  //环信设备ID
)

/**
 * 履历
 */
data class Cv(
        val id: Int,
        var created_on : String,                                         //创建时间
        var user : Int,                                                  //用户  ----狗屎 不知道为何文档 Int类型
        var name : String,                                               //简历名称
        var comments : String,                                           //自我介绍
        var position : String,                                           //求职意向
        var min_salary : String,                                         //最低薪资
        var max_alary : String,                                          //最高薪资
        var type : Any,                                                  //职位类型
        var province : String,                                           //省份
        var city : String,                                               //市
        var region : String,                                             //区
        var status : String,                                             //状态
        var onboard : String                                             //到岗时间
)

/**
 * 申请职位列表
 */
data class ApplyList(
        val id: Int,
        var created_on : String,                                         //创建时间
        var position : Position,                                         //职位详情
        var user : User,                                                 //用户详情
        var cv : Cv,                                                     //履历
        var status : String,                                             //状态        00 申请 01 HR同意 02HR拒绝 10 到场 11HR到场确认 12HR到场未确认 20 Offer发放 21入职确认 22入职拒绝
        // 30 入职完成 31离职 35完成兼职 36中途离场 90 撤销
        var interview_time : String,                                     //面试时间
        var interview_date : String,                                     //面试日期
        var onboard_date : String,                                       //入职日期
        var quit_date : String,                                          //离职日期
        var remark : String,                                             //备注
        var quit_remark : String,                                        //放弃理由

        var isSelect : Boolean = false                                   //是否选中
)

/**
 * 待付款信息
 */
data class Fund(
        var count : Int,                                                  //个人信息
        var amount : Double,                                              //总额
        var position : Int,                                               //职位
        var name : String                                                 //职位名称
)

/**
 * 申请
 */
data class Apply(
        val id: Int,
        var onboard_date : String,                                       //入职日期
        var quit_date : String                                           //离职日期
)

/**
 *
 */
data class Applys(
        var status : String,                                             //00 申请 01 HR同意 02HR拒绝  10 到场 11HR到场确认 12HR到场未确认 20 Offer发放 21入职确认 22入职拒绝 30 入职完成 31离职  35完成兼职 36中途离场 90 撤销
        var status_count : String                                        //数量
)

data class FundDetail(
        val id: Int,
        var user : User,                                                 //用户详情
        var apply : Apply,                                               //职位详情
        var position : Any,                                              //职位
        var status : Any,                                                //状态
        var amt : Double,                                                //
        var fund_date_est : String                                       //
)

data class CompanyStaffsBody(
        val count: Int,
        val next: Any,
        val previous: Any,
        val results: List<Result>
)

data class Result(
        val id: Int,
        val created_on: String,
        val type: String,
        val key: String,
        val value: String
)

data class Count(
        var apply_count: Int = 0,                                         //报名管理人数统计
        var interview_count: Int = 0,                                     //面试管理人数统计
        var onboard_count: Int=0,                                        //入职管理人数统计
        var payment_count: Int=0,                                        //结算管理人数统计
        var onsite_count: Int=0                                          //到场管理人数统计
)


data class WalletCo(
        val company: String,                                             //公司
        var created_on : String,                                         //创建时间
        var total_amt : Double =0.0,                                     //总金额
        var freeze_amt : Double =0.0,                                    //冻结金额
        var available_amt : Double =0.0,                                 //可用额度
        var credit_amt : Double =0.0,                                    //信用额度
        var loan_amt : Double =0.0                                       //贷款额度
)


data class CompanyStaffsResult(
        val id: Int,
        val created_on: String,
        val type: String,
        val key: String,
        val value: String
)

data class CareerTypeBody(
        val count: Int,
        val next: Any,
        val previous: Any,
        val results: List<CareerTypeResult>
)

data class CareerTypeResult(
        val id: Int,
        val skills: List<Any>,
        val created_on: String,
        val name: String,
        val status: String,
        val level: Int,
        val parent: Int
)


data class ReleasePopData(
        val position: Int,
        val key : String,
        val value: String,
        val id: Int,
        val parentId : Int,
        val skills: List<Any>?
)

data class WorkExpResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<WorkExpResult>
)

data class WorkExpResult(
    val id: Int,
    val created_on: String,
    val type: String,
    val key: String,
    val value: String
)
