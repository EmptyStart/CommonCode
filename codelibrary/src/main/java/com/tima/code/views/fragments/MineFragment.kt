package com.tima.code.views.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tima.code.R
import com.tima.code.responsebody.Company
import com.tima.code.responsebody.Hr
import com.tima.code.responsebody.LoginResponseBody
import com.tima.code.timaconstracts.IMinePresent
import com.tima.code.timaconstracts.IMineView
import com.tima.code.timapresenter.MinePresenterImpl
import com.tima.common.BusEvents.SelectEvent5
import com.tima.common.base.BaseFragment
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.ImageLoader
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.code_fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.litepal.LitePal

/**
 * 我的-界面
 * @author : zhijun.li on 2018/8/27
 *   email :
 */
@Route(path = RoutePaths.minefragment)
class MineFragment : BaseFragment(), View.OnClickListener, IMineView {
    override fun gotoChangeInfo() {
        if (Constant.position == 1) {
            ARouter.getInstance().build(RoutePaths.registerPrivate).navigation()
        } else {
//            ARouter.getInstance().build(RoutePaths.registerPrivate).navigation()
            ARouter.getInstance().build(RoutePaths.createcompany).navigation()
        }

    }

    var minePresent: IMinePresent? = null
    override fun useEventBus(): Boolean {
        return true
    }

    override fun attachLayoutRes(): Int {
        return R.layout.code_fragment_mine
    }

    override fun initView() {
        minePresent = MinePresenterImpl(this)

        ll_help.setOnClickListener(this)
        onlineService.setOnClickListener(this)
        ll_shell.setOnClickListener(this)
        ll_wallet.setOnClickListener(this)
        ll_resume.setOnClickListener(this)
        tvLogout.setOnClickListener(this)
        tv_company_name.setOnClickListener(this)
        iv_logo.setOnClickListener(this)

        putData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun getEvent(event: SelectEvent5) {
        if (event.upData == 2) {
            putData()
        }
        EventBus.getDefault().removeStickyEvent(event)
    }

    private fun putData() {
        val loginBody = LitePal.findFirst(LoginResponseBody::class.java)
        val hr = LitePal.findFirst(Hr::class.java)
        val company = LitePal.findFirst(Company::class.java)
        if (loginBody != null && hr != null && company != null) {
            if (Constant.position == 1) {
                tv_company_name.text = hr.name
                ll_resume.visibility = View.GONE
                mineVerfy.setText("个人实名认证")
            } else if (Constant.position == 2) {
                tv_company_name.text = company.full_name
                ll_resume.visibility = View.VISIBLE
                mineVerfy.setText("公司实名认证")
            }
            var url = loginBody.img_base + company.logo
            ImageLoader.load(activity, url, iv_logo, R.mipmap.code_register_addphoto)
            tv_phone.text = hr.mobile
        }
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        minePresent?.onClick(v)
    }

    override fun showLoading() {
        loadingManage.show()
    }

    override fun hideLoading() {
        loadingManage.dismiss()
    }

    override fun showError(errorMsg: String) {
    }

    override fun getMineActivity(): Activity {
        return activity
    }

    override fun callPhone() {
        val phoneNum: String? = tv_phone.text.toString().trim()
        if (phoneNum.isNullOrEmpty()) return
        val rxPermissions = RxPermissions(getMineActivity() as AppCompatActivity)
        rxPermissions.request(Manifest.permission.CALL_PHONE)
                .subscribe(object : Consumer<Boolean> {
                    override fun accept(t: Boolean?) {
                        t?.let {
                            if (it) {
                                val intent = Intent(Intent.ACTION_DIAL);
                                val data = Uri.parse("tel:" + phoneNum);
                                intent.setData(data);
                                getMineActivity().startActivity(intent);
                            } else {
                                toast("该功能需要电话权限！")
                            }
                        }
                    }
                })
    }
}