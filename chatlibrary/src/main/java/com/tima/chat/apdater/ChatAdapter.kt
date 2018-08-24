package com.tima.chat.apdater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.hyphenate.chat.EMMessage
import com.tima.chat.R
import com.tima.chat.bean.MsgInfo
import com.tima.chat.helper.ChatUtils.TYPE_SEND
import com.tima.chat.weight.CircleImageView
import com.tima.common.utils.DateUtils
import com.tima.common.utils.StringUtils

/**
 * 聊天适配器
 * Created by Administrator on 2018/8/21/021.
 */
class ChatAdapter(var context : Context, var msgInfos : List<MsgInfo>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return msgInfos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //var view = View.inflate(context, R.layout.chat_reycler_chat_layout,null)
        var view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_reycler_chat_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var msgInfo = msgInfos.get(position)
        if (msgInfo.type ==TYPE_SEND){                                                              //发送
            holder.llChartTo.visibility = View.VISIBLE
            holder.llChartFrom.visibility = View.GONE
            holder.chatto_time.text = StringUtils.getSubString(msgInfo.acceptTime,11,16)

            if (msgInfo.msgType == EMMessage.Type.TXT){
                holder.chatto_content.visibility = View.VISIBLE
                holder.ivChattoContent.visibility = View.GONE

                holder.chatto_content.text = msgInfo.content
            }else{
                holder.chatto_content.visibility = View.GONE
                holder.ivChattoContent.visibility = View.VISIBLE
            }
        }else{                                                                                      //接收
            holder.llChartTo.visibility = View.GONE
            holder.llChartFrom.visibility = View.VISIBLE
            holder.chatfrom_content.text = msgInfo.content
            holder.chatto_time.text = StringUtils.getSubString(msgInfo.acceptTime,11,16)


            if (msgInfo.msgType == EMMessage.Type.TXT){
                holder.chatfrom_content.visibility = View.VISIBLE
                holder.ivChatfromContent.visibility = View.GONE

                holder.chatfrom_content.text = msgInfo.content
            }else{
                holder.chatfrom_content.visibility = View.GONE
                holder.ivChatfromContent.visibility = View.VISIBLE
            }
        }

        setTitleTime(holder.chat_time,position)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var ivChatfrom: CircleImageView = itemView.findViewById(R.id.iv_chatfrom)                   //接收人头像
        var ivChatto:CircleImageView = itemView.findViewById(R.id.iv_chatto)                        //自己 头像
        var llChartFrom: LinearLayout = itemView.findViewById(R.id.ll_chart_from)                   //接收布局
        var llChartTo: LinearLayout = itemView.findViewById(R.id.ll_chart_to)                     //发送布局
        var chatfrom_content: TextView = itemView.findViewById(R.id.chatfrom_content)               //接收内容
        var chatfrom_time:TextView = itemView.findViewById(R.id.chatfrom_time)                      //时间
        var chatto_content: TextView = itemView.findViewById(R.id.chatto_content)                   //发送内容
        var chatto_time:TextView = itemView.findViewById(R.id.chatto_time)                          //时间
        var chat_time: TextView = itemView.findViewById(R.id.chat_time)                             //标题 日期
        var tv_from_name: TextView = itemView.findViewById(R.id.tv_from_name)                       //发送人姓名
        var ivChatfromContent: ImageView = itemView.findViewById(R.id.iv_chatfrom_content)          //接收图片、语音
        var ivChattoContent: ImageView = itemView.findViewById(R.id.iv_chatto_content)              //发送图片、语音
    }

    /**
     * 标题时间
     */
    private fun setTitleTime(textView: TextView, position: Int) {
        val time = msgInfos.get(position).acceptTime
        if (position == 0) {
            textView.text = StringUtils.getSubString(time, 6, 16)
            textView.visibility = View.VISIBLE
        } else {
            val beforeTime = msgInfos.get(position - 1).acceptTime
            if (DateUtils.getLongYMDHMS(time) - DateUtils.getLongYMDHMS(beforeTime) > 1000 * 60 * 2) {       //差距两分钟显示
                textView.text = StringUtils.getSubString(time, 6, 16)
                textView.visibility = View.VISIBLE
            } else {
                textView.visibility = View.GONE
            }
        }
    }


}

