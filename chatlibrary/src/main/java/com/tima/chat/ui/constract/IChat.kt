package com.tima.chat.constracts

import com.tima.chat.bean.MsgInfo
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener

/**
 * Created by Administrator on 2018/8/22/022.
 */
interface IChatViewModel : IBaseViewModel{
    fun addOnBodyDataListener(listener: IDataListener)
}

interface IChatPresent : IBasePresenter{

    fun onRefreshChatAdapter(msgInfos : ArrayList<MsgInfo>)                                         //刷新聊天界面

    fun startVoice()                                                                                //开始语音

    fun stopVoice()                                                                                 //结束语音


}

interface IChatView : IBaseViews