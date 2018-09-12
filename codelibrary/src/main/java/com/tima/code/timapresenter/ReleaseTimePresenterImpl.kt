package com.tima.code.timapresenter

import android.app.Activity
import android.app.ActivityManager
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.tima.code.R
import com.tima.code.ResponseBody.CareerTypeBody
import com.tima.code.ResponseBody.CareerTypeResult
import com.tima.code.timaconstracts.IRegisterPrivatePresent
import com.tima.code.timaconstracts.IReleaseTimePresent
import com.tima.code.timaconstracts.IReleaseTimeView
import com.tima.code.timaviewmodels.ReleaseTimeViewModelImpl
import com.tima.common.base.IDataListener
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.GsonUtils

/**
 * @author : zhijun.li on 2018/9/12
 *   email : zhijun.li@timanetworks.com
 *
 */
class ReleaseTimePresenterImpl(view: IReleaseTimeView) : IReleaseTimePresent{
    var mView: IReleaseTimeView? = view
    val careerTypes= arrayListOf<CareerTypeResult>()

    val mViewModel by lazy(LazyThreadSafetyMode.NONE){
        ReleaseTimeViewModelImpl()
    }
    override fun onClick(view: View?) {
        view?.let {
            when(it.id){

            }
        }
    }

    override fun saveRelease(){
        careerType()
    }
    private fun careerType(){
        mViewModel.addCareertype(object : IDataListener{
            override fun requestData(): Map<String, String>? {
                return null
            }

            override fun successData(success: String) {
                val careerTypeBody = GsonUtils.getGson.fromJson(success, CareerTypeBody::class.java)
                val results = careerTypeBody?.results
                results?.let {
                careerTypes.addAll(it)
                    careerTypes.forEach{

                    }
                }
            }

            override fun errorData(error: String) {
            }
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewModel.detachView()
        mView == null
    }

}