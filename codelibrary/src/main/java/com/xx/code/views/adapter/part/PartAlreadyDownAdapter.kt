package com.xx.code.views.adapter.part

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xx.code.R
import com.xx.code.responsebody.Position
import com.xx.common.utils.LogUtils
import com.xx.common.utils.StringUtils

/**
 * 管理-兼职-已下架
 * Created by Administrator on 2018/8/29/029.
 */

class PartAlreadyDownAdapter(var layoutId : Int, var datas: List<Position>): BaseQuickAdapter<Position, BaseViewHolder>(layoutId,datas){
    override fun convert(helper: BaseViewHolder?, item: Position?) {
        LogUtils.i(TAG,"位置=="+helper?.layoutPosition+"    item=="+item.toString())
        helper?.setText(R.id.tv_describe,item?.name)
        helper?.setText(R.id.tv_interview_time,item?.interview_time)
        helper?.setText(R.id.tv_company_name,item?.company)
        var location = (if (item?.province == item?.city) item?.province else item?.province + StringUtils.SPACE_TWO + item?.city) + StringUtils.SPACE_TWO + item?.region + StringUtils.SPACE_TWO +item?.address
        helper?.setText(R.id.tv_address,location)

        helper?.addOnClickListener(R.id.tv_release_status)
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
