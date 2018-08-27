package com.tima.code.views.activitys

import android.app.FragmentTransaction
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.code.timaconstracts.IMainPagePresent
import com.tima.code.timaconstracts.IMainPageView
import com.tima.code.timapresenter.MainPagePresenterImpl
import com.tima.code.views.fragments.FragmentUtils
import com.tima.code.views.fragments.ReleaseFragment
import com.tima.common.BusEvents.SelectPos1
import com.tima.common.base.BaseActivity
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ColorIdUtil
import com.tima.common.utils.LogUtils
import kotlinx.android.synthetic.main.code_activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/22
 *   email : zhijun.li@timanetworks.com
 *  个人主页
 */
@Route(path = RoutePaths.mainpage)
class MainPageActivity : BaseActivity(), View.OnClickListener, IMainPageView {

    var currentPosition: Int = 0
    /**
     * 0普通用户 1登录的求职 2登录的发布职位
     */
    var office: Int = 0;

    var present: IMainPagePresent? = null


    var releaseFragment: ReleaseFragment? = null
    var fragment2: FragmentUtils? = null
    var fragment3: FragmentUtils? = null
    var fragment4: FragmentUtils? = null

    override fun useEventBus(): Boolean = true


    override fun getLayoutId(): Int {

        return R.layout.code_activity_main
    }

    override fun inits(savedInstanceState: Bundle?) {
        office = Constant.getPosition()
        present = MainPagePresenterImpl(this)
        LogUtils.i("MainPageActivity", office.toString());
        defaultTab()
    }


    override fun onClick(v: View?) {
        present?.onClick(v)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun reciviceBus(event: SelectPos1) {
        if (event.isPos1) {
//            tabSelect(3)
        }
        EventBus.getDefault().removeStickyEvent(event)
    }


    /**
     * 初始化tab
     */
    fun defaultTab() {
        ll_tab1.setOnClickListener(this)
        ll_tab2.setOnClickListener(this)
        ll_tab3.setOnClickListener(this)
        ll_tab4.setOnClickListener(this)

        iv_tab1.tag = false
        iv_tab2.tag = false
        iv_tab3.tag = false
        iv_tab4.tag = false

        tabSelect(0)
//        changeFragment(0)
    }

    override fun tabSelect(position: Int) {

        if (iv_tab1.tag as Boolean) {
            iv_tab1.tag = false
            iv_tab1.setImageResource(R.mipmap.code_main_release_nor)
            tv_tab1.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab2.tag as Boolean) {
            iv_tab2.tag = false
            iv_tab2.setImageResource(R.mipmap.code_main_manage_nor)
            tv_tab2.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab3.tag as Boolean) {
            iv_tab3.tag = false
            iv_tab3.setImageResource(R.mipmap.code_main_message_nor)
            tv_tab3.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab4.tag as Boolean) {
            iv_tab4.tag = false
            iv_tab4.setImageResource(R.mipmap.code_main_my_nor)
            tv_tab4.setTextColor(ColorIdUtil.getColorId(R.color.code_main_textcolor))
        }

        when (position) {
            0 -> {
                if (!(iv_tab1.getTag() as Boolean)) {
                    iv_tab1.tag = true
                    iv_tab1.setImageResource(R.mipmap.code_main_release_pre)
                    tv_tab1.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
            1 -> {
                if (!(iv_tab2.getTag() as Boolean)) {
                    iv_tab2.tag = true
                    iv_tab2.setImageResource(R.mipmap.code_main_manage_pre)
                    tv_tab2.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
            2 -> {
                if (!(iv_tab3.getTag() as Boolean)) {
                    iv_tab3.tag = true
                    iv_tab3.setImageResource(R.mipmap.code_main_message_pre)
                    tv_tab3.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
            3 -> {
                if (!(iv_tab4.getTag() as Boolean)) {
                    iv_tab4.tag = true
                    iv_tab4.setImageResource(R.mipmap.code_main_my_pre)
                    tv_tab4.setTextColor(ColorIdUtil.getColorId(R.color.code_title_barbc))
                }
            }
        }

        changeFragment(position)
    }

    private fun changeFragment(position: Int) {
        val transaction = fragmentManager.beginTransaction()
        hideFragment(position, transaction)
        when (position) {
            0 -> {
                if (releaseFragment == null) {
                    releaseFragment = ARouter.getInstance().build(RoutePaths.reasefragment).navigation() as
                            ReleaseFragment
                    transaction.add(R.id.fl_container, releaseFragment)
                } else {
                    transaction.show(releaseFragment)
                }
            }
            1 -> {
                if (fragment2 == null) {
                    fragment2 = ARouter.getInstance().build(RoutePaths.fragmentutils).withString("title", position.toString() + "1").navigation() as FragmentUtils
                    transaction.add(R.id.fl_container, fragment2)
                } else {
                    transaction.show(fragment2)
                }
            }
            2 -> {
                if (fragment3 == null) {
                    fragment3 = ARouter.getInstance().build(RoutePaths.fragmentutils).withString("title", position.toString() + "1").navigation() as FragmentUtils
                    transaction.add(R.id.fl_container, fragment3)
                } else {
                    transaction.show(fragment3)
                }
            }
            3 -> {
                if (fragment4 == null) {
                    fragment4 = ARouter.getInstance().build(RoutePaths.fragmentutils).withString("title", position.toString() + "1").navigation() as FragmentUtils
                    transaction.add(R.id.fl_container, fragment4)
                } else {
                    transaction.show(fragment4)
                }
            }
        }
        transaction.commit()

    }

    private fun hideFragment(position: Int, transaction: FragmentTransaction) {
        if (position != 0) {
            releaseFragment?.let {
                transaction.hide(it)
            }
        }
        if (position != 1) {
            fragment2?.let {
                transaction.hide(it)
            }
        }
        if (position != 2) {
            fragment3?.let {
                transaction.hide(it)
            }
        }
        if (position != 3) {
            fragment4?.let {
                transaction.hide(it)
            }
        }
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }
}