package com.tima.chat.apdater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.tima.chat.R
import com.tima.chat.bean.MsgInfo
import com.tima.chat.weight.CircleImageView

/**
 * 聊天适配器
 * Created by Administrator on 2018/8/21/021.
 */
class ChatAdapter(var context : Context, var msgInfos : List<MsgInfo>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return msgInfos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(context, R.layout.chat_reycler_chat_layout,null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var ivChatfrom: CircleImageView = itemView.findViewById(R.id.iv_chatfrom)                   //接收人头像
        var ivChatto:CircleImageView = itemView.findViewById(R.id.iv_chatto)                        //自己 头像
        var llChartFrom: LinearLayout = itemView.findViewById(R.id.ll_chart_from)                   //接收布局
        var rlChartTo: RelativeLayout = itemView.findViewById(R.id.rl_chart_to)                     //发送布局
        var chatfrom_content: TextView = itemView.findViewById(R.id.chatfrom_content)               //接收内容
        var chatfrom_time:TextView = itemView.findViewById(R.id.chatfrom_time)                      //时间
        var chatto_content: TextView = itemView.findViewById(R.id.chatto_content)                   //发送内容
        var chatto_time:TextView = itemView.findViewById(R.id.chatto_time)                          //时间
        var chat_time: TextView = itemView.findViewById(R.id.chat_time)                             //标题 日期
        var tv_from_name: TextView = itemView.findViewById(R.id.tv_from_name)                       //发送人姓名
        var ivChatfromContent: ImageView = itemView.findViewById(R.id.iv_chatfrom_content)          //接收图片、语音
        var ivChattoContent: ImageView = itemView.findViewById(R.id.iv_chatto_content)              //发送图片、语音
    }


}

