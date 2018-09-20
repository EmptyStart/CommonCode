package com.tima.code.views.adapter

import android.content.Context
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.common.base.PresonInfo
import com.tima.common.utils.ImageLoader
import com.tima.common.utils.LogUtils

/**
 * 消息-互动-适配器
 * Created by Administrator on 2018/8/28/028.
 */

class InteractionAdapter(layoutId : Int, datas: List<PresonInfo>, var context: Context): BaseQuickAdapter<PresonInfo, BaseViewHolder>(layoutId,datas) {
    var TAG = "InteractionAdapter"
    override fun convert(helper: BaseViewHolder?, item: PresonInfo?) {
        LogUtils.i(TAG,"位置=="+helper?.layoutPosition+"    item=="+item.toString())
        helper?.setText(R.id.tv_name,item?.name)
        if (item?.newsCount == 0){
            helper?.setVisible(R.id.tv_news_num,false)
        }else{
            helper?.setVisible(R.id.tv_news_num,true)
            if(item?.newsCount!! > 99){
                helper?.setText(R.id.tv_news_num,"99+")
            }else{
                helper?.setText(R.id.tv_news_num,item?.newsCount.toString())
            }
        }
        if (!TextUtils.isEmpty(item?.headUrl) && item?.headUrl?.length!! > 0){
            ImageLoader.load(context,item?.headUrl,helper?.itemView?.findViewById(R.id.iv_head))
        }
    }
}

/*
class InteractionAdapter(var context : Context,var listener:OnInteractionListener) : RecyclerView.Adapter<InteractionAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_interaction_item,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onInteractionClick()
        })
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tv_news_num : TextView = itemView.findViewById(R.id.tv_news_num)
        var tv_name : TextView = itemView.findViewById(R.id.tv_name)
        var iv_head : ImageView = itemView.findViewById(R.id.iv_head)
    }

    interface OnInteractionListener{
        fun onInteractionClick()
    }
}*/
