package com.tima.code.views.fragments

import android.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.common.base.BaseFragment
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ColorIdUtil
import kotlinx.android.synthetic.main.code_fragment_news.*
/**
 *  消息
 * Created by Administrator on 2018/8/27/027.
 */
@Route(path = RoutePaths.newsfragment)
class NewsFragment : BaseFragment() , View.OnClickListener{

    var communicateFragment : CommunicateFragment? = null
    var interactionFragment : InteractionFragment? = null

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_news
    }

    override fun initView() {
        ll_interaction.setOnClickListener(this)
        ll_communicate.setOnClickListener(this)

        ll_interaction.tag = false
        ll_communicate.tag = false
        tabSelect(0)
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_communicate->{
                tabSelect(0)
            }
            R.id.ll_interaction->{
                tabSelect(1)
            }
        }
    }

    fun tabSelect(position: Int) {
        if (ll_communicate.tag as Boolean) {
            ll_communicate.tag = false
            tv_communicate.setTextColor(ColorIdUtil.getColorId(R.color.text_gray))
            line_communicate.setBackgroundColor(ColorIdUtil.getColorId(R.color.text_gray))
        }
        if (ll_interaction.tag as Boolean) {
            ll_interaction.tag = false
            tv_interaction.setTextColor(ColorIdUtil.getColorId(R.color.text_gray))
            line_interaction.setBackgroundColor(ColorIdUtil.getColorId(R.color.text_gray))
        }
        when(position){
            0->{
                if (!(ll_communicate.tag as Boolean)) {
                    ll_communicate.tag = true
                    tv_communicate.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                    line_communicate.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
            1->{
                if (!(ll_interaction.tag as Boolean)) {
                    ll_interaction.tag = true
                    tv_interaction.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                    line_interaction.setBackgroundColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
        }
        changeFragment(position)
    }

    fun changeFragment(position: Int) {
        val transaction = fragmentManager.beginTransaction()
        hideFragment(position, transaction)
        when (position) {
            0 -> {
                if (communicateFragment == null) {
                    communicateFragment = ARouter.getInstance().build(RoutePaths.communicatefragment).navigation() as CommunicateFragment
                    transaction.add(R.id.fl_news_layout, communicateFragment)
                } else {
                    transaction.show(communicateFragment)
                }
            }
            1 -> {
                if (interactionFragment == null) {
                    interactionFragment = ARouter.getInstance().build(RoutePaths.interactionfragment).navigation() as InteractionFragment
                    transaction.add(R.id.fl_news_layout, interactionFragment)
                } else {
                    transaction.show(interactionFragment)
                }
            }
        }
    }

    fun hideFragment(position: Int, transaction: FragmentTransaction) {
        if (position != 0) {
            communicateFragment?.let {
                transaction.hide(it)
            }
        }
        if (position != 1) {
            interactionFragment?.let {
                transaction.hide(it)
            }
        }
    }

}