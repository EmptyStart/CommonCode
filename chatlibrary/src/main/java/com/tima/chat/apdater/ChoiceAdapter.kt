package com.tima.chat.apdater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tima.chat.R
import com.tima.chat.weight.CircleImageView

/**
 * Created by Administrator on 2018/8/22/022.
 */
class ChoiceAdapter(var context: Context, var listener : OnChoiceClickListener) : RecyclerView.Adapter<ChoiceAdapter.ViewHolder>(){
    private val names = arrayOf("照片", "拍摄")
    private val icons = intArrayOf(R.mipmap.photo_icon, R.mipmap.shot_icon)

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(context, R.layout.chat_list_add_chat_layout,null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivIcon!!.setImageResource(icons[position])
        holder.tvName!!.setText(names[position])
        holder.ivIcon!!.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onClickItem(names[position])
        })
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
    }

    interface OnChoiceClickListener {
        fun onClickItem(name: String)
    }
}