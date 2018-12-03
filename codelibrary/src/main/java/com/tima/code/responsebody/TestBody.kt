package com.tima.code.responsebody

/**
 * @author : zhijun.li on 2018/9/11
 * email :
 */


data class TestBody(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<ResultTest>
)

data class ResultTest(
    val id: Int,
    val position: PositionTest,
    val user: UserTest,
    val cv: CvTest,
    val created_on: String,
    val status: String,
    val interview_time: String,
    val interview_date: String,
    val onboard_date: String,
    val quit_date: String,
    val remark: String,
    val quit_remark: String
)

data class PositionTest(
    val id: Int,
    val career_type: CareerType,
    val created_on: String,
    val name: String,
    val province: String,
    val city: String,
    val region: String,
    val address: String,
    val longitude: String,
    val latitude: String,
    val type: String,
    val salary_type: String,
    val salary_begin: Any,
    val salary_end: Any,
    val salary_unit: String,
    val salary_qty: Any,
    val exp_year_beg: Any,
    val exp_year_end: Any,
    val on_site_time: Any,
    val education: String,
    val status: String,
    val verify: String,
    val priority: Any,
    val descriptions: String,
    val remark: Any,
    val interview_day: String,
    val interview_time: String,
    val interview_from: Any,
    val interview_to: Any,
    val margin: Any,
    val qty: Any,
    val qty_var: Any,
    val qty_apply: Any,
    val skill_set: Any,
    val company: Int,
    val hr: Int
)

data class CareerType(
    val id: Int,
    val name: String,
    val status: String,
    val level: Int,
    val parent: Int
)

data class UserTest(
    val id: Int,
    val certificates: List<Any>,
    val experiences: List<Any>,
    val educations: List<EducationTest>,
    val created_on: String,
    val mobile: String,
    val name: Any,
    val nick_name: Any,
    val password: Any,
    val avatar: String,
    val type: Any,
    val gender: String,
    val address: Any,
    val province: Any,
    val city: Any,
    val region: Any,
    val email: Any,
    val birthyear: Any,
    val rating: Any,
    val status: String,
    val cv_status: String,
    val register_ip: Any,
    val last_login_ip: String,
    val last_login_on: Any,
    val last_login_device: Any,
    val tel: Any,
    val top_diploma: String,
    val work_years: Any,
    val img1: Any,
    val img2: Any,
    val img3: Any,
    val id_card: Any,
    val id_verified: String,
    val ref_code: Any,
    val push_id: Any,
    val im_id: Any
)

data class EducationTest(
    val id: Int,
    val user: Int,
    val cv: Int,
    val school: String,
    val graduated_on: String,
    val diploma: String,
    val major: String,
    val created_on: String
)

data class CvTest(
    val id: Int,
    val user: Int,
    val name: String,
    val comments: String,
    val position: String,
    val min_salary: Any,
    val max_alary: Any,
    val type: String,
    val province: String,
    val city: String,
    val region: String,
    val status: String,
    val onboard: String,
    val created_on: String
)