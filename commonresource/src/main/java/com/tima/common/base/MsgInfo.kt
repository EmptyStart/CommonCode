package com.tima.common.base

import org.litepal.crud.DataSupport

/**
 * 聊天信息
 * Created by Administrator on 2018/8/21/021.
 */
class MsgInfo : DataSupport(){
    var msgId : String = ""                                         //消息id
    var content: String = ""                                        //消息内容
    var type: Int = -1                                              //消息类型-接收发送
    var msgType: com.hyphenate.chat.EMMessage.Type = com.hyphenate.chat.EMMessage.Type.TXT                //信息类型-语音-图片-文本
    var voiceLeght : Int = 0                                        //语音-长度
    var acceptTime : String =""                                     //接收最新消息时间  yyyy-MM-dd HH:mm:ss
    var from : String = ""                                          //好友id
    var isRead : Int = 0                                            //已读表示 0、未读  1、已读


    override fun toString(): String {
        return "MsgInfo(content='$content', type=$type, msgType=$msgType, voiceLeght=$voiceLeght, acceptTime='$acceptTime', from='$from')"
    }
}

/**
 * 聊天对象信息
 */
class PresonInfo(
        var name: String ,                                              //名称
        var userName: String,                                           //账号
        var newsCount: Int,                                             //消息数量
        var headUrl: String                                             //头像路径
) : DataSupport()