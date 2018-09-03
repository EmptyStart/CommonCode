package com.tima.code.views.activitys

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tima.code.R
import com.tima.code.timaconstracts.IPersonnelRosterView
import com.tima.code.timapresenter.PersonnelRosterPresenterImpl
import com.tima.common.base.BaseActivity
import kotlinx.android.synthetic.main.code_activity_personnel_roster.*

/**
 * 人员名单-界面
 * Created by Administrator on 2018/9/1/001.
 */
class PersonnelRosterActivity : BaseActivity() ,IPersonnelRosterView, View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    var personnelRoster : PersonnelRosterPresenterImpl? = null
    override fun getLayoutId(): Int {
        return R.layout.code_activity_personnel_roster
    }

    override fun inits(savedInstanceState: Bundle?) {
        personnelRoster = PersonnelRosterPresenterImpl(this)
        personnelRoster?.init()
        abv_type2.setOnRightImageListener(this)
        tv_sure_payment.setOnClickListener(this)
        swipe_personnel_roster.setOnRefreshListener(this)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }

    override fun onFinish() {
        finish()
    }

    override fun getRecyclerPersonnelView(): RecyclerView {
        return recycler_personnel_roster
    }

    override fun getPersonnelActivity(): Activity {
        return this
    }

    override fun onClick(v: View?) {
        personnelRoster?.onClick(v)
    }

    override fun onRefresh() {
        swipe_personnel_roster.isRefreshing = false
    }
}
