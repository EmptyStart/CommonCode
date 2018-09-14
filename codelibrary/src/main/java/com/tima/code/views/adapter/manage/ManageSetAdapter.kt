package com.tima.code.views.adapter.manage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.ResponseBody.ApplyList
import com.tima.common.utils.LogUtils
import java.util.ArrayList

/**
 * 管理-集合-适配器
 * Created by Administrator on 2018/9/3/003.
 */

class ManageSetAdapter(var layoutId : Int, var datas: ArrayList<ApplyList>,var status : String,var type : Int): BaseQuickAdapter<ApplyList, BaseViewHolder>(layoutId,datas) ,Filterable{

    override fun convert(helper: BaseViewHolder?, item: ApplyList?) {
        helper?.setImageResource(R.id.iv_manage_set_check,if (item?.isSelect!!) R.mipmap.ic_total_check_light else R.mipmap.ic_total_check_dark)
        helper?.setText(R.id.tv_manage_set_name,item?.user?.name)
        helper?.setText(R.id.tv_manage_set_remarks,"("+item?.position?.name+")")

        helper?.addOnClickListener(R.id.iv_manage_set_check)
        helper?.addOnClickListener(R.id.tv_manage_set_two)
        helper?.addOnClickListener(R.id.tv_manage_set_one)

        if (status == "00"){
            helper?.setVisible(R.id.tv_manage_set_one,true)
            helper?.setVisible(R.id.tv_manage_set_two,true)

            helper?.setText(R.id.tv_manage_set_one,"同意")
            helper?.setText(R.id.tv_manage_set_two,"拒绝")
        }else if (status == "10"){
            helper?.setVisible(R.id.tv_manage_set_one,true)
            helper?.setVisible(R.id.tv_manage_set_two,true)

            helper?.setText(R.id.tv_manage_set_one,"到场")
            helper?.setText(R.id.tv_manage_set_two,"未到场")
        }else if (status == "11"){
            helper?.setVisible(R.id.tv_manage_set_one,true)
            helper?.setVisible(R.id.tv_manage_set_two,true)
            if (type == 3){
                helper?.setText(R.id.tv_manage_set_one,"完成")
                helper?.setText(R.id.tv_manage_set_two,"中途离场")
            }else{
                helper?.setText(R.id.tv_manage_set_one,"入职邀请")
                helper?.setText(R.id.tv_manage_set_two,"拒绝")
            }
        }else if (status == "21"){
            helper?.setVisible(R.id.tv_manage_set_one,true)
            helper?.setVisible(R.id.tv_manage_set_two,true)

            helper?.setText(R.id.tv_manage_set_one,"到场")
            helper?.setText(R.id.tv_manage_set_two,"未到场")
        }else {
            helper?.setVisible(R.id.tv_manage_set_one,false)
            helper?.setVisible(R.id.tv_manage_set_two,false)
        }
    }


    private var filter: FriendListFilter? = null                                                         // 创建MyFilter对象
    override fun getFilter(): Filter {
        if (filter == null) {
            filter = FriendListFilter(datas)
        }
        return filter as FriendListFilter
    }

    /**
     * 创建内部类MyFilter继承Filter类，并重写相关方法，实现数据的过滤
     */
    internal inner class FriendListFilter(var list: ArrayList<ApplyList>) : Filter() {
        private var cacheFriends = ArrayList<ApplyList>()                                 //创建集合保存原始数据
        init {
            cacheFriends = list
        }

        /**
         * 该方法返回搜索过滤后的数据
         */
        override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
            val results = Filter.FilterResults()

            if (TextUtils.isEmpty(charSequence)) {
                results.values = cacheFriends
                results.count = cacheFriends.size
            } else {
                val mList = ArrayList<ApplyList>()                                        // 创建集合保存过滤后的数据
                for (applyList in cacheFriends) {
                    if (!TextUtils.isEmpty(applyList.position.name) && applyList.position.name.contains(charSequence.toString())) {
                        mList.add(applyList)
                    }
                }
                results.values = mList
                results.count = mList.size
            }
            return results
        }

        /**
         * 该方法用来刷新用户界面，根据过滤后的数据重新展示列表
         */
        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
            mData = filterResults.values as ArrayList<ApplyList>
            notifyDataSetChanged()
        }
    }
}
