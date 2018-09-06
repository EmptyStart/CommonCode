package com.tima.code.views.adapter.full

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.ResponseBody.Position
import com.tima.common.utils.LogUtils
import com.tima.common.utils.StringUtils

/**
 * 消息-全职-已发
 * Created by Administrator on 2018/8/29/029.
 */

class FullPublishedAdapter(layoutId : Int, datas: List<Position>): BaseQuickAdapter<Position, BaseViewHolder>(layoutId,datas) {
    var TAG = "FullPublishedAdapter"
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
class FullPublishedAdapter(var context : Context, var listener : OnPublishedListener): RecyclerView.Adapter<FullPublishedAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_full_published_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var positionInfo = positionInfos[position]
        holder.tv_describe.text = positionInfo.name
        holder.tv_interview_time.text = positionInfo.interview_time
        holder.tv_company_name.text = positionInfo.company
        holder.tv_address.text = positionInfo.provice + StringUtils.SPACE_TWO+positionInfo.city+StringUtils.SPACE_TWO+positionInfo.region+StringUtils.SPACE_TWO+positionInfo.address

        holder.tv_release_status.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onPublishClickRelase()
        })
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onPublishedClickItem()
        })

    }

    override fun getItemCount(): Int {
        return positionInfos.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tv_describe : TextView = itemView.findViewById(R.id.tv_describe)                        //职位名称---促销员（全职）
        var tv_interview_time : TextView = itemView.findViewById(R.id.tv_interview_time)            //面试时间---2018-07-30:14:00
        var tv_company_name : TextView = itemView.findViewById(R.id.tv_company_name)                //公司名称----
        var tv_address : TextView = itemView.findViewById(R.id.tv_address)                          //工作地址----上海 长宁区 复兴路
        var tv_release_status : TextView = itemView.findViewById(R.id.tv_release_status)            //发布状态----下架
    }

    interface OnPublishedListener{
        fun onPublishedClickItem()

        fun onPublishClickRelase()
    }
}*/
