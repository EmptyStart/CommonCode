package com.tima.code.views.adapter.full

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R

/**
 *  全职要求
 * Created by Administrator on 2018/8/29/029.
 */

class ManageFullInfoRequireAdapter(layoutId : Int,  datas: List<String>): BaseQuickAdapter<String, BaseViewHolder>(layoutId,datas){
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_position,(helper?.layoutPosition+1).toString()+". ")
        helper?.setText(R.id.tv_require_content,item)
    }
}


/*
class ManageFullInfoRequireAdapter(var context : Context, var listener : OnFullInfoRequireListener, var requires :  ArrayList<String>): RecyclerView.Adapter<ManageFullInfoRequireAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_manage_full_info_require_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onFullInfoRequireClickItem()
        })
        holder.tv_position.text = (position+1).toString()+". "
        holder.tv_require_content.text = requires[position]
    }

    override fun getItemCount(): Int {
        return requires.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_position: TextView = itemView.findViewById(R.id.tv_position)
        var tv_require_content: TextView = itemView.findViewById(R.id.tv_require_content)
    }

    interface OnFullInfoRequireListener {
        fun onFullInfoRequireClickItem()
    }
}*/
