package com.tima.chat.apdater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.tima.chat.R

/**
 *  选择拍照等功能 适配器
 * Created by Administrator on 2018/8/22/022.
 */
class ChoiceFunctionAdapter(var context: Context,var listener : OnChoiceClickListener) : BaseAdapter() {
    private val names = arrayOf("照片", "拍摄")
    private val icons = intArrayOf(R.mipmap.photo_icon, R.mipmap.shot_icon)


    override fun getView(i: Int, convertView: View?, p2: ViewGroup?): View {
        var view  = convertView
        val hodler: ViewHodler
        if (view == null) {
            hodler = ViewHodler()
            view = LayoutInflater.from(context).inflate(R.layout.chat_list_add_chat_layout, null)
            hodler.ivIcon = view.findViewById(R.id.iv_icon)
            hodler.tvName = view.findViewById(R.id.tv_name)
            view.setTag(hodler)
        } else {
            hodler = view.getTag() as ViewHodler
        }
        hodler.ivIcon!!.setImageResource(icons[i])
        hodler.tvName!!.setText(names[i])
        hodler.ivIcon!!.setOnClickListener(View.OnClickListener {
            if (listener != null)
                listener.onClickItem(names[i])
        })
        return view!!
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return names.size
    }

    internal inner class ViewHodler {
        var ivIcon: ImageView? = null
        var tvName: TextView? = null
    }

    interface OnChoiceClickListener {
        fun onClickItem(name: String)
    }
}
