package com.xx.chat.apdater

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hyphenate.chat.EMMessage
import com.hyphenate.helpdesk.easeui.recorder.MediaManager
import com.xx.chat.R
import com.xx.chat.helper.ChatUtils.TYPE_SEND
import com.xx.chat.weight.CircleImageView
import com.xx.common.base.MsgInfo
import com.xx.common.utils.DateUtils
import com.xx.common.utils.DensityUtils
import com.xx.common.utils.LogUtils
import com.xx.common.utils.StringUtils
import java.io.File

/**
 * 聊天适配器
 * Created by Administrator on 2018/8/21/021.
 */
class ChatAdapter(var context : Context, var msgInfos : List<MsgInfo>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    var TAG = "ChatAdapter"
    var mDensity: Float = DensityUtils.getDensity(context)

    init {
        initAudio()
    }

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

            if (msgInfo.msgType == EMMessage.Type.TXT){                                             //文字
                holder.chatto_content.visibility = View.VISIBLE
                holder.ivChattoContent.visibility = View.GONE
                holder.llChattoVoice.visibility = View.GONE

                holder.chatto_content.text = msgInfo.content
            }else if (msgInfo.msgType == EMMessage.Type.VOICE){                                      //语音
                holder.llChattoVoice.visibility = View.VISIBLE
                holder.tvChattoVoiceLength.text = msgInfo.voiceLeght.toString()
                //holder.ivChattoVoiceType.width = DensityUtil.getVoiceWidth((msgInfo.voiceLeght * mDensity).toInt())
                //holder.ivChattoContent.layoutParams.width = DensityUtil.getVoiceWidth((msgInfo.voiceLeght * mDensity).toInt())
                holder.ivChattoContent.setImageResource(R.mipmap.send_3)
                holder.ivChattoContent.setOnClickListener(View.OnClickListener {
                   /* if (mp != null ){
                        if (mp!!.isPlaying ){
                            pauseVoice()
                        }else{
                            mp?.reset()
                            if (!TextUtils.isEmpty(msgInfo.content)){
                                var mFIS = FileInputStream(msgInfo.content)
                                var mFD = mFIS.fd
                                try{
                                    mp?.setDataSource(mFD)
                                    mp?.setAudioStreamType(AudioManager.STREAM_VOICE_CALL)
                                    mp?.prepare()
                                    playVoice()
                                }catch (e : Exception){
                                    e.printStackTrace()
                                    Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
                                }finally {
                                     if (mFIS != null)
                                         mFIS.close()
                                }
                            }else{
                                LogUtils.i(TAG,"语音路径==="+msgInfo.content)
                            }
                        }
                    }*/
                    playFromVoice(msgInfo.content)
                })
            }else{
                holder.chatto_content.visibility = View.GONE
                holder.ivChattoContent.visibility = View.VISIBLE
            }
        }else{                                                                                      //接收
            holder.llChartTo.visibility = View.GONE
            holder.llChartFrom.visibility = View.VISIBLE
            holder.chatfrom_content.text = msgInfo.content
            holder.chatfrom_time.text = StringUtils.getSubString(msgInfo.acceptTime,11,16)
            holder.tv_from_name.text = msgInfo.from

            if (msgInfo.msgType == EMMessage.Type.TXT){
                holder.chatfrom_content.visibility = View.VISIBLE
                holder.ivChatfromContent.visibility = View.GONE
                holder.llChatfromVoice.visibility = View.GONE

                holder.chatfrom_content.text = msgInfo.content
            }else{
                holder.chatfrom_content.visibility = View.GONE
                holder.llChatfromVoice.visibility = View.VISIBLE
                holder.ivChatfromContent.visibility = View.VISIBLE

                holder.ivChatfromContent.setImageResource(R.mipmap.receive_3)
                holder.tvChatfromVoiceLength.text = msgInfo.voiceLeght.toString()
                holder.ivChatfromContent.setOnClickListener(View.OnClickListener {
                    playFromVoice(msgInfo.content)
                })
            }
        }

        setTitleTime(holder.chat_time,position)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var ivChatfrom: CircleImageView = itemView.findViewById(R.id.iv_chatfrom)                   //接收人头像
        var ivChatto:CircleImageView = itemView.findViewById(R.id.iv_chatto)                        //自己 头像
        var llChartFrom: LinearLayout = itemView.findViewById(R.id.ll_chart_from)                   //接收布局
        var llChartTo: LinearLayout = itemView.findViewById(R.id.ll_chart_to)                       //发送布局
        var chatfrom_content: TextView = itemView.findViewById(R.id.chatfrom_content)               //接收内容
        var chatfrom_time:TextView = itemView.findViewById(R.id.chatfrom_time)                      //时间
        var chatto_content: TextView = itemView.findViewById(R.id.chatto_content)                   //发送内容
        var chatto_time:TextView = itemView.findViewById(R.id.chatto_time)                          //时间
        var chat_time: TextView = itemView.findViewById(R.id.chat_time)                             //标题 日期
        var tv_from_name: TextView = itemView.findViewById(R.id.tv_from_name)                       //发送人姓名
        var ivChatfromContent: ImageView = itemView.findViewById(R.id.iv_chatfrom_content)          //接收图片、语音
        var ivChattoContent: ImageView = itemView.findViewById(R.id.iv_chatto_content)              //发送图片、语音


        var llChatfromVoice: LinearLayout = itemView.findViewById(R.id.ll_chatfrom_voice)           //接收 语音
        var ivChatfromVoiceType: ImageView = itemView.findViewById(R.id.iv_chatfrom_voice_type)     //接收 语音 - 状态
        var tvChatfromVoiceLength: TextView = itemView.findViewById(R.id.tv_chatfrom_voice_length) //接收 语音 - 长度

        var llChattoVoice: LinearLayout = itemView.findViewById(R.id.ll_chatto_voice)               //发送 语音
        var ivChattoVoiceType: ImageView = itemView.findViewById(R.id.iv_chatto_voice_type)         //发送 语音 - 状态
        var tvChattoVoiceLength: TextView = itemView.findViewById(R.id.tv_chatto_voice_length)      //发送 语音 - 长度
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


    var mp : MediaPlayer? = null
    fun initAudio(){
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        var maxVoice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        LogUtils.i(TAG,"最大语音 maxVoice=="+maxVoice)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVoice, AudioManager.FLAG_PLAY_SOUND)

        audioManager.mode = AudioManager.MODE_NORMAL
        mp = MediaPlayer()
        mp?.setAudioStreamType(AudioManager.STREAM_RING)
        mp?.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra -> false })
    }

    fun pauseVoice() {
        mp?.pause()
    }

    fun playVoice() {
        mp?.start()
        mp?.setOnCompletionListener {
            mp?.reset()
        }
    }

    /**
     *  播放接收到语音
     */
    fun playFromVoice(localPath : String){
        val file = File(localPath)
        if (file.exists()) {
            MediaManager.playSound(context, localPath,MediaPlayer.OnCompletionListener() {
                mediaPlayer ->
            })
        } else {
            Toast.makeText(context, com.hyphenate.helpdesk.R.string.is_down_please_wait, Toast.LENGTH_SHORT).show()
        }
    }
}

