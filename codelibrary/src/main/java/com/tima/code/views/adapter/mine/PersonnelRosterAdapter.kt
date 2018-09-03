package com.tima.code.views.adapter.mine

import android.support.v4.content.ContextCompat
import android.support.v4.graphics.ColorUtils
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.tima.code.R

/**
 * 人员名单-适配器
 * Created by Administrator on 2018/9/1/001.
 */
class PersonnelRosterAdapter(var context : android.content.Context, var listener : OnPersonnelRosterListener): RecyclerView.Adapter<PersonnelRosterAdapter.ViewHolder>(){

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
}
