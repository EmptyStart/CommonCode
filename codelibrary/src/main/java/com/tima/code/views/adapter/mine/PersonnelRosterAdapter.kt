package com.tima.code.views.adapter.mine

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tima.code.R
import com.tima.code.responsebody.FundDetail
import com.tima.common.utils.ResourceUtil

/**
 * 人员名单-适配器
 * Created by Administrator on 2018/9/1/001.
 */

class PersonnelRosterAdapter(var layoutId : Int, var datas: List<FundDetail>): BaseQuickAdapter<FundDetail, BaseViewHolder>(layoutId,datas){
    @SuppressLint("ResourceAsColor")
    override fun convert(helper: BaseViewHolder?, item: FundDetail?) {
        var position = helper?.layoutPosition
        if (item?.apply?.quit_date == null){
            helper?.setText(R.id.tv_quit,"离职")
            helper?.setBackgroundRes(R.id.tv_quit,R.drawable.radius_solid_black)
            helper?.setTextColor(R.id.tv_quit, ResourceUtil.getColorId(R.color.white))
            helper?.setVisible(R.id.ll_departure_time,false)
            helper?.setText(R.id.tv_departure_time,item?.apply?.quit_date)

            helper?.addOnClickListener(R.id.tv_quit)
        }else{
            helper?.setText(R.id.tv_quit,"已离职")
            helper?.setBackgroundRes(R.id.tv_quit,R.drawable.radius_solid_gray)
            helper?.setTextColor(R.id.tv_quit, ResourceUtil.getColorId(R.color.text_gray))
            helper?.setVisible(R.id.ll_departure_time,true)
        }

        helper?.setText(R.id.tv_position_name,item?.user?.name)
        helper?.setText(R.id.tv_phone,item?.user?.mobile)
        helper?.setText(R.id.tv_entry_time,item?.apply?.onboard_date)
        helper?.setText(R.id.tv_money,"￥"+item?.amt)
    }
}

/*class PersonnelRosterAdapter(var context : Context, var listener : OnPersonnelRosterListener): RecyclerView.Adapter<PersonnelRosterAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_personnel_roster_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onPersonnelRosterClick()
        })
        if (position == 0 || position % 2 == 0){
            holder.ll_departure_time.visibility = View.GONE
            holder.tv_quit.setBackgroundResource(R.drawable.radius_solid_black)
            holder.tv_quit.setTextColor(ContextCompat.getColor(context,R.color.white))
            holder.tv_quit.text = "离职"
        }else{
            holder.ll_departure_time.visibility = View.VISIBLE
            holder.tv_quit.setBackgroundResource(R.drawable.radius_solid_gray)
            holder.tv_quit.setTextColor(ContextCompat.getColor(context,R.color.text_gray))
            holder.tv_quit.text = "已离职"
        }
    }

    override fun getItemCount(): Int {
        return 6
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var ll_departure_time : LinearLayout = itemView.findViewById(R.id.ll_departure_time)
        var tv_departure_time : TextView = itemView.findViewById(R.id.tv_departure_time)
        var tv_quit : TextView = itemView.findViewById(R.id.tv_quit)
        var tv_money : TextView = itemView.findViewById(R.id.tv_money)
        var tv_phone : TextView = itemView.findViewById(R.id.tv_phone)
        var tv_position_name : TextView = itemView.findViewById(R.id.tv_position_name)
    }

    interface OnPersonnelRosterListener{
        fun onPersonnelRosterClick()
    }
}*/
