package com.xx.code.views.adapter.part

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xx.code.R
import com.xx.code.responsebody.Position
import com.xx.common.utils.LogUtils

/**
 * 管理-兼职-审核中
 * Created by Administrator on 2018/8/29/029.
 */
class PartAuditAdapter(var layoutId : Int, var datas: List<Position>): BaseQuickAdapter<Position, BaseViewHolder>(layoutId,datas){

    override fun convert(helper: BaseViewHolder?, item: Position?) {
        LogUtils.i(TAG,"位置=="+helper?.layoutPosition+"    item=="+item.toString())
        helper?.setText(R.id.tv_describe,item?.name)

        var qty = if(item?.qty == null) "0/" else (item?.qty.toString() + "/")
        var qty_var = if(item?.qty_var == null) "0人" else (item?.qty_var.toString() + "人")
        helper?.setText(R.id.tv_number,qty+qty_var)
        helper?.setText(R.id.tv_release_time,item?.created_no)
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