package com.tima.code.views.adapter.full

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.bean.JobType

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

/*
class ManageFullInfoJobAdapter(var context : Context, var listener : OnFullInfoJodListener, var jobs :  ArrayList<JobType>): RecyclerView.Adapter<ManageFullInfoJobAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_manage_full_info_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var jobInfo = jobs[position]
        holder.tv_job_name.text = jobInfo.jobName
        if (!TextUtils.isEmpty(jobInfo.jobType)){
            holder.tv_job_type.text = jobInfo.jobType
        }
        holder.tv_job_type.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onFullInfoJodClickItem()
        })
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tv_job_type : TextView = itemView.findViewById(R.id.tv_job_type)
        var tv_job_name : TextView = itemView.findViewById(R.id.tv_job_name)
    }

    interface OnFullInfoJodListener{
        fun onFullInfoJodClickItem()
    }
}*/
