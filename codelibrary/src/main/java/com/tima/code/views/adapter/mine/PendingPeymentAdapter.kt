package com.tima.code.views.adapter.mine

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.ResponseBody.Fund

/**
 *  待付款-适配器
 * Created by Administrator on 2018/9/1/001.
 */

class PendingPeymentAdapter(var layoutId : Int, var datas: List<Fund>?): BaseQuickAdapter<Fund, BaseViewHolder>(layoutId,datas){
    override fun convert(helper: BaseViewHolder?, item: Fund?) {
        helper?.setText(R.id.tv_position_name,item?.name)
        helper?.setText(R.id.tv_number,item?.count.toString()+"人")
        helper?.setText(R.id.tv_money,"￥"+item?.amount)

        helper?.addOnClickListener(R.id.tv_payment)
    }

}

/*class PendingPeymentAdapter(var context : android.content.Context, var listener : OnPendingPeymentListener): RecyclerView.Adapter<PendingPeymentAdapter.ViewHolder>(){

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
}*/
