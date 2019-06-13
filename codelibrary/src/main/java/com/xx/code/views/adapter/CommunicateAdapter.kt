package com.xx.code.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xx.code.R

/**
 * 消息-沟通-适配器
 * Created by Administrator on 2018/8/28/028.
 */
class CommunicateAdapter(var context : Context,var listener : OnCommunicateListener): RecyclerView.Adapter<CommunicateAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_communicate_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_notify.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onCommunicateClick()
        })
    }

    override fun getItemCount(): Int {
        return 10
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tv_notify : TextView = itemView.findViewById(R.id.tv_notify)
        var tv_describe : TextView = itemView.findViewById(R.id.tv_describe)
        var tv_see_vitae : TextView = itemView.findViewById(R.id.tv_see_vitae)
    }

    interface OnCommunicateListener{
        fun onCommunicateClick()
    }
}