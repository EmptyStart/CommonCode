package com.tima.code.views.activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.BusEvents.CanSelectTag
import com.tima.common.BusEvents.SelectedTag
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.code_activity_tagselect.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * @author : lzj on 2018/9/16 9:51
 *         # Email
 */
@Route(path = RoutePaths.releasetag)
class TagSelectActivity : BaseActivity(), View.OnClickListener {

    var selectPosition = hashSetOf<Int>()
    val data1 = arrayListOf<String>()
    val data2 = arrayListOf<String>()
    var selectedTagAdapter: TagAdapter<String>? = null
    var unSelectedTagAdapter: TagAdapter<String>? = null

    override fun useEventBus(): Boolean {
        return true
    }

    override fun getLayoutId(): Int {

        return R.layout.code_activity_tagselect
    }

    override fun inits(savedInstanceState: Bundle?) {
        actionbar.setOnRightImageListener(View.OnClickListener { finish() })
        actionbar.setOnRightTextListener(View.OnClickListener {
            if (data1.size>0){
                val selectedTag = SelectedTag()
                selectedTag.tagList.addAll(data1)
                EventBus.getDefault().post(selectedTag)
            }
            finish()
        })
        inputTag.setOnClickListener(this)
        selectedTagAdapter = object : TagAdapter<String>(data1) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout
                        .code_tag_select_item1, parent, false) as FrameLayout
                val textView = view.find(R.id.tag) as TextView
                textView.setText(t)
                return view

            }
        }
        selectedTag.adapter = selectedTagAdapter
        selectedTag.setOnTagClickListener { view, position, parent ->
            data1.removeAt(position)
            selectedTagAdapter?.notifyDataChanged()
            selectPosition.remove(selectPosition.elementAt(position))
            unSelectedTagAdapter?.setSelectedList(selectPosition)
            true
        }
        unSelectedTagAdapter = object : TagAdapter<String>(data2) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val textView = LayoutInflater.from(parent?.context).inflate(R.layout
                        .code_tag_select_item2, parent, false) as TextView
                textView.setText(t)
                return textView
            }
        }
        unselectTag.adapter = unSelectedTagAdapter
        unselectTag.setMaxSelectCount(3)
        unselectTag.setOnSelectListener {
            data1.clear()
            selectPosition.clear()
            it.forEach {
                data1.add(data2.get(it))
                selectPosition.add(it)
            }
            selectedTagAdapter?.notifyDataChanged()
            if (it.size == 3) {
                toast("你最多只能选择3个！")
            }
        }
    }

    override fun onClick(v: View?) {
        val text = etTag.text.toString()
        if (text.isNotEmpty()) {
            data2.add(text)
        }
        unSelectedTagAdapter?.notifyDataChanged()
        unSelectedTagAdapter?.setSelectedList(selectPosition)
        etTag.setText("")
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun dataTag(array: CanSelectTag) {
        data2.addAll(array.tagList)
        if (data2.size>0){
            unSelectedTagAdapter?.notifyDataChanged()
        }
        EventBus.getDefault().removeStickyEvent(array)
    }
}