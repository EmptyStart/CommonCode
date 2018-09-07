package com.tima.code.views.adapter.part

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.ResponseBody.Position

/**
 * 消息-兼职-已发布
 * Created by Administrator on 2018/8/28/028.
 */

class PartPublishedAdapter(var layoutId : Int, var datas: List<Position>): BaseQuickAdapter<Position,BaseViewHolder>(layoutId,datas){
    override fun convert(helper: BaseViewHolder?, item: Position?) {
    }

}
