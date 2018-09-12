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

data class LoginResponseBody (
        val hr: Hr,
        val company: String,
        val token: String,
        val img_base: String,
        val parttime_commition: Double
)

data class Hr(
        val id: Int,
        val created_on: String,
        val mobile: String,
        val name: Any,
        val nick_name: Any,
        val password: Any,
        val avatar: Any,
        val type: String,
        val gender: String,
        val address: Any,
        val province: Any,
        val city: Any,
        val region: Any,
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
        val im_id: Any,
        val company: Int
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
        var education : String,                                          //要求最低学历    0 不限，1 初中及以下，2 中专/中技，3 高中，4 大专，5 本科，6 硕士，7 博士
        var status : String,                                             //状态            1 正常 2 禁用 3 删除
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
        var career_type : Int,                                           //职位类型
        var hr : Int                                                     //Hr 人事
)

/**
 * 证书
 */
class Certificate(
        val id: Int,
        var created_on: String,                                          //创建时间
        var get_date : String,                                           //获取时间
        var name : String,                                               //证书名称
        var user : Any,                                                  //用户  ----狗屎 不知道为何文档 Int类型
        var cv : Any                                                     //简历
)

/**
 * 经验
 */
class Experience(
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
class Education(
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
        val certificates: Certificate,                                   //证书
        var experiences : Experience,                                    //经验
        var educations : Education,                                      //学历
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
class Cv(
        val id: Int,
        var created_on : String,                                         //创建时间
        var user : Any,                                                  //用户  ----狗屎 不知道为何文档 Int类型
        var name : String,                                               //简历名称
        var comments : Any,                                              //自我介绍
        var position : Any,                                              //求职意向
        var min_salary : Int,                                            //最低薪资
        var max_alary : Int,                                             //最高薪资
        var type : Any,                                                  //职位类型
        var province : String,                                           //省份
        var city : String,                                               //市
        var region : String,                                             //区
        var status : Any,                                                //状态
        var onboard : Any                                                //到岗时间
)

/**
 * 申请列表
 */
class ApplyList(
        val id: Int,
        var created_on : String,                                         //创建时间
        var position : Position,                                         //职位详情
        var user : User,                                                 //用户详情
        var cv : Cv,                                                     //履历
        var status : Any,                                                //状态
        var interview_time : Any,                                        //面试时间
        var interview_date : String,                                     //面试日期
        var onboard_date : Any,                                          //入职日期
        var quit_date : Any,                                             //离职日期
        var remark : Int,                                                //备注
        var quit_remark : Int                                            //放弃理由
)

/**
 * 待付款信息
 */
class Fund(
        var count : Int,                                                  //个人信息
        var amount : Double,                                              //总额
        var position : Int,                                               //职位
        var name : String                                                 //职位名称
)

/**
 * 申请
 */
class Apply(
        val id: Int,
        var onboard_date : String,                                          //入职日期
        var quit_date : String                                              //离职日期
)

class FundDetail(
    val id: Int,
    var user : User,                                                    //用户详情
    var apply : Apply,                                                  //职位详情
    var position : Any,                                                 //职位
    var status : Any,                                                   //状态
    var amt : Double,                                                   //
    var fund_date_est : String                                          //
)


data class CompanyStaffsBody(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<CompanyStaffsResult>
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
