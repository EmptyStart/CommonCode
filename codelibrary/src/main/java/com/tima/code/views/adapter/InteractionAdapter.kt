package com.tima.code.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tima.code.R

/**
 * 消息-互动-适配器
 * Created by Administrator on 2018/8/28/028.
 */
class InteractionAdapter(var context : Context,var listener:OnInteractionListener) : RecyclerView.Adapter<InteractionAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_interaction_item,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onInteractionClick()
        })
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tv_news_num : TextView = itemView.findViewById(R.id.tv_news_num)
        var tv_name : TextView = itemView.findViewById(R.id.tv_name)
        var iv_head : ImageView = itemView.findViewById(R.id.iv_head)
    }

    interface OnInteractionListener{
        fun onInteractionClick()
    }
}