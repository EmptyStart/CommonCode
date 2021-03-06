package com.xx.code.views.activitys

import android.app.FragmentTransaction
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.location.AMapLocation
import com.xx.chat.helper.ChatUtils
import com.xx.code.R
import com.xx.code.constracts.IMainPagePresent
import com.xx.code.constracts.IMainPageView
import com.xx.code.presenter.MainPagePresenterImpl
import com.xx.code.views.fragments.*
import com.xx.common.base.BaseActivity
import com.xx.common.base.Constant
import com.xx.common.base.RoutePaths
import com.xx.common.services.IChatManage
import com.xx.common.utils.*
import kotlinx.android.synthetic.main.code_activity_main.*
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/8/22
 *   email :
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
    var manageFragment: ManageFragment? = null
    var newsFragment: NewsFragment? = null
    var minefragment: MineFragment? = null
    val iChatManage by lazy(LazyThreadSafetyMode.NONE) {
        ARouter.getInstance().build(RoutePaths
                .chatManager).navigation() as IChatManage
    }
    fun getLoc(){
        MapGaoDe.getLocation(this,object :IAMapLocationSuccessListener{
            override fun onLocationChanged(p0: AMapLocation?) {
                Constant.aMapLocation=p0
            }
        })
    }
    override fun getLayoutId(): Int {

        return R.layout.code_activity_main
    }

    override fun inits(savedInstanceState: Bundle?) {
//        office = Constant.getPosition()
        present = MainPagePresenterImpl(this)
        lifecycle.addObserver(present!!)
        LogUtils.i("MainPageActivity", office.toString());
        defaultTab()
        initChat()
        getLoc()
    }

    /**
     * 初始化环信聊天
     */
    fun initChat() {
        /*val initChat = iChatManage.initChat()
        if (initChat) {
            val mobile = Constant.mobile
            val chatPassword = Constant.chatPassword
            if (mobile.isNotEmpty()&&chatPassword.isNotEmpty()) {
                iChatManage.login(mobile, chatPassword)
            }
        }*/
        var result = ChatUtils.init(this)
        if (result) {
            if (!TextUtils.isEmpty(Constant.mobile) && !TextUtils.isEmpty(Constant.chatPassword)){
                var result = ChatUtils.login(Constant.mobile, Constant.chatPassword, this)
            }else{
                toast("初始化环信账号、密码为空!")
            }
        }else{
            toast("初始化环信失败")
        }
    }

    override fun onClick(v: View?) {
        present?.onClick(v)
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
        if (TextUtils.equals(Constant.verify,"2")){
            tabSelect(3)
        }else {
            tabSelect(0)
        }
    }

    override fun tabSelect(position: Int) {

        if (iv_tab1.tag as Boolean) {
            iv_tab1.tag = false
            iv_tab1.setImageResource(R.mipmap.code_main_release_nor)
            tv_tab1.setTextColor(ResourceUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab2.tag as Boolean) {
            iv_tab2.tag = false
            iv_tab2.setImageResource(R.mipmap.code_main_manage_nor)
            tv_tab2.setTextColor(ResourceUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab3.tag as Boolean) {
            iv_tab3.tag = false
            iv_tab3.setImageResource(R.mipmap.code_main_message_nor)
            tv_tab3.setTextColor(ResourceUtil.getColorId(R.color.code_main_textcolor))
        }
        if (iv_tab4.tag as Boolean) {
            iv_tab4.tag = false
            iv_tab4.setImageResource(R.mipmap.code_main_my_nor)
            tv_tab4.setTextColor(ResourceUtil.getColorId(R.color.code_main_textcolor))
        }

        when (position) {
            0 -> {
                if (!(iv_tab1.getTag() as Boolean)) {
                    iv_tab1.tag = true
                    iv_tab1.setImageResource(R.mipmap.code_main_release_pre)
                    tv_tab1.setTextColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                }
            }
            1 -> {
                if (!(iv_tab2.getTag() as Boolean)) {
                    iv_tab2.tag = true
                    iv_tab2.setImageResource(R.mipmap.code_main_manage_pre)
                    tv_tab2.setTextColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                }
            }
            2 -> {
                if (!(iv_tab3.getTag() as Boolean)) {
                    iv_tab3.tag = true
                    iv_tab3.setImageResource(R.mipmap.code_main_message_pre)
                    tv_tab3.setTextColor(ResourceUtil.getColorId(R.color.code_title_barbc))
                }
            }
            3 -> {
                if (!(iv_tab4.getTag() as Boolean)) {
                    iv_tab4.tag = true
                    iv_tab4.setImageResource(R.mipmap.code_main_my_pre)
                    tv_tab4.setTextColor(ResourceUtil.getColorId(R.color.code_title_barbc))
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
                    releaseFragment = ARouter.getInstance().build(RoutePaths.releasefragment).navigation() as
                            ReleaseFragment
                    transaction.add(R.id.fl_container, releaseFragment)
                } else {
                    transaction.show(releaseFragment)
                }
            }
            1 -> {
                if (manageFragment == null) {
                    manageFragment = ARouter.getInstance().build(RoutePaths.managefragment).navigation() as ManageFragment
                    transaction.add(R.id.fl_container, manageFragment)
                } else {
                    transaction.show(manageFragment)
                }
            }
            2 -> {
                if (newsFragment == null) {
                    newsFragment = ARouter.getInstance().build(RoutePaths.newsfragment).navigation() as NewsFragment
                    transaction.add(R.id.fl_container, newsFragment)
                } else {
                    transaction.show(newsFragment)
                }
            }
            3 -> {
                if (minefragment == null) {
                    minefragment = ARouter.getInstance().build(RoutePaths.minefragment).navigation() as MineFragment
                    transaction.add(R.id.fl_container, minefragment)
                } else {
                    transaction.show(minefragment)
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
            manageFragment?.let {
                transaction.hide(it)
            }
        }
        if (position != 2) {
            newsFragment?.let {
                transaction.hide(it)
            }
        }
        if (position != 3) {
            minefragment?.let {
                transaction.hide(it)
            }
        }
    }


    override fun showLoading() {
        loadingBar.show()
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            //iChatManage.loginOut()
            ChatUtils.loginOut(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}