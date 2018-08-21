package com.tima.code.bean

import com.hyphenate.chat.EMMessage

/**
 * Created by Administrator on 2018/8/21/021.
 */
class MsgInfo{
    val TYPE_RECEIVED = 0                                           //消息的类型:接收
    val TYPE_SEND = 1                                               //消息的类型:发送
    //val MSGTYPE_VOICE = 2                                           //信息的类型:语音
    //val MSGTYPE_IMAGE = 3                                           //信息的类型:图片
    //val MSGTYPE_TEXT = 4                                            //信息的类型:文本

    var content: String = ""                                        //消息内容
    var type: Int = -1                                              //消息类型-接收发送
    var msgType: EMMessage.Type = EMMessage.Type.TXT                //信息类型-语音-图片-文本
    var voiceLeght : Int = 0                                        //语音-长度
    var acceptTime : String =""                                     //接收最新消息时间  yyyy-MM-dd HH:mm:ss
    var from : String = ""                                          //接收人
}