package com.tima.code.views.adapter.mine

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tima.code.R

/**
 *  待付款-适配器
 * Created by Administrator on 2018/9/1/001.
 */
class PendingPeymentAdapter(var context : android.content.Context, var listener : OnPendingPeymentListener): RecyclerView.Adapter<PendingPeymentAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_pending_payment_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onPendingPeymentClick()
        })
    }

    override fun getItemCount(): Int {
        return 6
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    }

    interface OnPendingPeymentListener{
        fun onPendingPeymentClick()
    }
}
