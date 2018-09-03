package com.tima.code.views.adapter.mine

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tima.code.R

/**
 *  交易流水-适配器
 *  Created by Administrator on 2018/8/31/031.
 */
class TradeFlowAdapter(var context : android.content.Context, var listener : OnTradeFlowListener): RecyclerView.Adapter<TradeFlowAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_trade_flow_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onClickTradeFlowItem()
        })
    }

    override fun getItemCount(): Int {
        return 6
    }


    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
    }

    interface OnTradeFlowListener{
        fun onClickTradeFlowItem()
    }
}
