package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tima.code.R
import com.tima.code.timaconstracts.IPersonnelRosterPresent
import com.tima.code.timaconstracts.IPersonnelRosterView
import com.tima.code.timaviewmodels.PersonnelRosterViewModelImpl
import com.tima.code.views.adapter.mine.PersonnelRosterAdapter
import kotlinx.android.synthetic.main.code_activity_personnel_roster.*
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2018/9/1/001.
 */
class PersonnelRosterPresenterImpl : IPersonnelRosterPresent,PersonnelRosterAdapter.OnPersonnelRosterListener {

    var personView : IPersonnelRosterView
    var activity : Activity
    var personnelAdapter : PersonnelRosterAdapter? = null
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { PersonnelRosterViewModelImpl() }
    constructor(view : IPersonnelRosterView){
        personView = view
        activity = personView.getPersonnelActivity()
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_actionbar_cancle->{
                activity.finish()
            }
            R.id.tv_sure_payment->{
                activity.toast("确认支付")
            }
        }
    }

    override fun init() {
        onRefreshAdapter()
    }

    override fun onRefreshAdapter() {
        if (personnelAdapter == null){
            personnelAdapter = PersonnelRosterAdapter(activity,this)
            personView.getRecyclerPersonnelView().layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            personView.getRecyclerPersonnelView().adapter = personnelAdapter
        }else{
            personnelAdapter?.notifyDataSetChanged()
        }
    }

    override fun onPersonnelRosterClick() {
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
    }
}