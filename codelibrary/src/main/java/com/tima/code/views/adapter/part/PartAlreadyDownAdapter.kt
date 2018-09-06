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
 * 管理-兼职-已下架
 * Created by Administrator on 2018/8/29/029.
 */

class PartAlreadyDownAdapter(var layoutId : Int, var datas: List<String>): BaseQuickAdapter<String, BaseViewHolder>(layoutId,datas){
    override fun convert(helper: BaseViewHolder?, item: String?) {
    }
}

/*
class PartAlreadyDownAdapter(var context : Context, var listener : OnAlreadyDownListener): RecyclerView.Adapter<PartAlreadyDownAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartAlreadyDownAdapter.ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_published_item,parent,false)
        return PartAlreadyDownAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onAlreadyDownClick()
        })
    }

    override fun getItemCount(): Int {
        return 4
    }


    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
    }

    interface OnAlreadyDownListener{
        fun onAlreadyDownClick()
    }
}*/
