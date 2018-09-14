package com.tima.code.timaconstracts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.tima.common.base.IBasePresenter
import com.tima.common.base.IBaseViewModel
import com.tima.common.base.IBaseViews
import com.tima.common.base.IDataListener
import com.tima.common.https.HttpRequest

/**
 * Created by Administrator on 2018/9/3/003.
 */
interface IManageSetModel : IBaseViewModel{
    fun addManageSetListener(listener: IDataListener, url : String,request: HttpRequest)
}

interface IManageSetView : IBaseViews {
    fun getManageActivity() : Activity

    fun getManageSetOneRecyclerView() : RecyclerView

    fun getManageSetTwoRecyclerView() : RecyclerView

    fun getManageSetThreeRecyclerView() : RecyclerView

    fun getEdSearchView() : EditText

    fun getTextSelectOneView() : TextView

    fun getTextSelectTwoView() : TextView

    fun getTextSelectThreeView() : TextView

    fun getIvTotalCheckView() : ImageView

    fun getLlBottomView() : LinearLayout

    fun getIdInt() : Int

    fun getmanageSetTypeInt() : Int
}
interface IManageSetPresent : IBasePresenter{
    fun toSelect(position : Int)

    fun onOneRefreshAdapter()
}