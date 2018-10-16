package com.tima.code.views.fragments

import android.app.FragmentTransaction
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.code.views.fragments.news.CommunicateFragment
import com.tima.code.views.fragments.news.InteractionFragment
import com.tima.common.base.BaseFragment
import com.tima.common.base.MsgInfo
import com.tima.common.base.RoutePaths
import com.tima.common.utils.DateUtils
import com.tima.common.utils.ResourceUtil
import kotlinx.android.synthetic.main.code_double_select_top.*
import org.litepal.LitePal

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
        ll_one.setOnClickListener(this)
        ll_two.setOnClickListener(this)

        ll_one.tag = false
        ll_two.tag = false
        tabSelect(0)

        LitePal.deleteAll(MsgInfo::class.java,"acceptTime < ?",DateUtils.getDateYMDHMS(System.currentTimeMillis() - 30 * 24 * 60 * 1000))
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_one->{
                tabSelect(0)
            }
            R.id.ll_two->{
                tabSelect(1)
            }
        }
    }

    fun tabSelect(position: Int) {
        if (ll_one.tag as Boolean) {
            ll_one.tag = false
            tv_one.setTextColor(ResourceUtil.getColorId(R.color.text_gray))
            line_one.visibility = View.GONE
        }
        if (ll_two.tag as Boolean) {
            ll_two.tag = false
            tv_two.setTextColor(ResourceUtil.getColorId(R.color.text_gray))
            line_two.visibility = View.GONE
        }
        when(position){
            0->{
                if (!(ll_one.tag as Boolean)) {
                    ll_one.tag = true
                    tv_one.setTextColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    line_one.visibility = View.VISIBLE
                }
            }
            1->{
                if (!(ll_two.tag as Boolean)) {
                    ll_two.tag = true
                    tv_two.setTextColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                    line_two.visibility = View.VISIBLE
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
        transaction.commit()
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