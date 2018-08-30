package com.tima.code.bean

/**
 * 职位类型-信息
 * Created by Administrator on 2018/8/29/029.
 */
class jobInfo{

    constructor(jobName: String,jobType: String?){
        this.jobName = jobName
        this.jobType = jobType
    }
    var jobName: String = ""                                        //职位类型-名称

    var jobType: String? = null                                        //职位-选中类型
}