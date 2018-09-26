package com.tima.code.views.adapter.part

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.responsebody.Position

/**
 * 消息-兼职-已发布
 * Created by Administrator on 2018/8/28/028.
 */

class PartPublishedAdapter(var layoutId : Int, var datas: List<Position>): BaseQuickAdapter<Position,BaseViewHolder>(layoutId,datas){
    override fun convert(helper: BaseViewHolder?, item: Position?) {
    }

}
