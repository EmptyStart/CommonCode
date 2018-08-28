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
 * 消息-兼职-已发布
 * Created by Administrator on 2018/8/28/028.
 */
class PublishedAdapter (var context : Context,var listener : OnPublishedListener): RecyclerView.Adapter<PublishedAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_published_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    }

    interface OnPublishedListener{
        fun onPublishedClick()
    }
}