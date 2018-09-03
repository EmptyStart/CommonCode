package com.tima.code.views.adapter.manage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tima.code.R

/**
 * 管理-集合-适配器
 * Created by Administrator on 2018/9/3/003.
 */
class ManageSetAdapter(var context : Context, var listener : OnManageSetListener): RecyclerView.Adapter<ManageSetAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_manage_set_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.iv_manage_set_check.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onManageSetCheckClick()
        })
        holder.tv_manage_set_two.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onManageSetTwoClick()
        })
        holder.tv_manage_set_one.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onManageSetOneClick()
        })
    }

    override fun getItemCount(): Int {
        return 2
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var iv_manage_set_check : ImageView = itemView.findViewById(R.id.iv_manage_set_check)
        var tv_manage_set_two : TextView = itemView.findViewById(R.id.tv_manage_set_two)
        var tv_manage_set_one : TextView = itemView.findViewById(R.id.tv_manage_set_one)
        var tv_manage_set_remarks : TextView = itemView.findViewById(R.id.tv_manage_set_remarks)
        var tv_manage_set_name : TextView = itemView.findViewById(R.id.tv_manage_set_name)
        var iv_manage_set_head : ImageView = itemView.findViewById(R.id.iv_manage_set_head)

    }

    interface OnManageSetListener{
        fun onManageSetCheckClick()

        fun onManageSetOneClick()

        fun onManageSetTwoClick()
    }
}
