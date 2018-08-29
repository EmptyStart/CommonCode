package com.tima.code.views.adapter.full

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tima.code.R

/**
 * 消息-全职-已发
 * Created by Administrator on 2018/8/29/029.
 */
class FullPublishedAdapter(var context : Context, var listener : OnPublishedListener): RecyclerView.Adapter<FullPublishedAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_full_published_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_release_status.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onPublishClickRelase()
        })
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onPublishedClickItem()
        })

    }

    override fun getItemCount(): Int {
        return 10
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tv_describe : TextView = itemView.findViewById(R.id.tv_describe)
        var tv_interview_time : TextView = itemView.findViewById(R.id.tv_interview_time)
        var tv_company_name : TextView = itemView.findViewById(R.id.tv_company_name)
        var tv_address : TextView = itemView.findViewById(R.id.tv_address)
        var tv_release_status : TextView = itemView.findViewById(R.id.tv_release_status)
    }

    interface OnPublishedListener{
        fun onPublishedClickItem()

        fun onPublishClickRelase()
    }
}