package com.xx.code.views.adapter.full

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xx.code.R
import com.xx.code.bean.JobType

/**
 * 职业类型等适配器
 * Created by Administrator on 2018/8/29/029.
 */
class ManageFullInfoJobAdapter(var layoutId : Int, var datas: List<JobType>): BaseQuickAdapter<JobType, BaseViewHolder>(layoutId,datas) {

    override fun convert(helper: BaseViewHolder?, item: JobType?) {
        helper?.setText(R.id.tv_job_name,item?.jobName)
        helper?.setText(R.id.tv_job_type,item?.jobType)
    }
}

