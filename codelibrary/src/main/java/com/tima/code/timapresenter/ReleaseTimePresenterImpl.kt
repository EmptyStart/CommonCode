package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.tima.code.ResponseBody.CareerTypeBody
import com.tima.code.ResponseBody.CareerTypeResult
import com.tima.code.ResponseBody.ReleasePopData
import com.tima.code.timaconstracts.IReleaseTimePresent
import com.tima.code.timaconstracts.IReleaseTimeView
import com.tima.code.timaconstracts.OnSelectListener
import com.tima.code.timaviewmodels.ReleaseTimeViewModelImpl
import com.tima.common.base.IDataListener
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.GsonUtils

/**
 * @author : zhijun.li on 2018/9/12
 *   email : zhijun.li@timanetworks.com
 *
 */
class ReleaseTimePresenterImpl(view: IReleaseTimeView) : IReleaseTimePresent {
    var mView: IReleaseTimeView? = view
    val popData = arrayListOf<ReleasePopData>()
    val careerTypes = arrayListOf<CareerTypeResult>()

    val mViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ReleaseTimeViewModelImpl()
    }

    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {

            }
        }
    }

    override fun saveRelease() {
        careerType(0)
    }

    private fun careerType(code :Int) {
        if (careerTypes.isEmpty()) {
            mViewModel.addCareertype(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return null
                }

                override fun successData(success: String) {
                    val careerTypeBody = GsonUtils.getGson.fromJson(success, CareerTypeBody::class.java)
                    val results = careerTypeBody?.results
                    careerTypes.clear()
                    results?.let {
                        careerTypes.addAll(it)
                    }
                    careerTypes.forEach {
                        if (it.parent==code){
                            popData.add(ReleasePopData(0,"",it.name,it.id,it.parent))
                        }
                    }
                }

                override fun errorData(error: String) {
                    ExceptionDeal.handleException(error)
                }
            })
        }else {
            careerTypes.forEach {
                if (it.parent == code) {
                    popData.add(ReleasePopData(0, "", it.name, it.id,it.parent))
                }
            }
        }
        if (popData.isNotEmpty()){
            mView?.showPop(popData,object : OnSelectListener{
                override fun selected(result: ReleasePopData) {
                    popData.clear()
                    careerTypes.forEach {
                        if (it.parent==result.parentId){
                            popData.add(ReleasePopData(0,"",it.name,it.id,it.parent))
                        }
                    }
                    if (popData.isNotEmpty()){
                        mView?.showPop(popData,object : OnSelectListener{
                            override fun selected(result: ReleasePopData) {

                            }
                        })
                    }else{

                    }
                }
            })
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewModel.detachView()
        mView == null
    }

}