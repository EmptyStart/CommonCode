package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.view.View
import com.tima.code.R
import com.tima.code.timaconstracts.IMinePresent
import com.tima.code.timaconstracts.IMineView
import com.tima.code.timaviewmodels.MineViewModelImpl
import com.tima.code.views.activitys.WalletActivity
import com.tima.common.base.IBaseViews

/**
 * Created by Administrator on 2018/8/30/030.
 */
class MinePresenterImpl : IMinePresent {
    var view : IMineView? = null
    var mineActivity : Activity? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { MineViewModelImpl() }
    constructor(view : IBaseViews){
        this.view = view as IMineView
        mineActivity = view?.getMineActivity()
    }
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.ll_help->{

            }
            R.id.tv_phone->{

            }
            R.id.ll_shell->{

            }
            R.id.ll_wallet->{
                var intent = Intent(mineActivity,WalletActivity::class.java)
                mineActivity!!.startActivity(intent)
            }
            R.id.ll_resume->{

            }
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }

}