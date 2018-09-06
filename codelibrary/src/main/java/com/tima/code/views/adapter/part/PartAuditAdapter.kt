package com.tima.code.views.adapter.part

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R

/**
 * 管理-兼职-审核中
 * Created by Administrator on 2018/8/29/029.
 */
class PartAuditAdapter(var layoutId : Int, var datas: List<String>): BaseQuickAdapter<String, BaseViewHolder>(layoutId,datas){
    override fun convert(helper: BaseViewHolder?, item: String?) {
    }
}

/*class PartAuditAdapter(var context : android.content.Context, var listener : PartAuditAdapter.OnAuditListener): RecyclerView.Adapter<PartAuditAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartAuditAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_published_item,parent,false)
        return PartAuditAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onAuditClick()
        })
    }

    override fun getItemCount(): Int {
        return 6
    }


    class ViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView){
    }

    interface OnAuditListener{
        fun onAuditClick()
    }
}*/