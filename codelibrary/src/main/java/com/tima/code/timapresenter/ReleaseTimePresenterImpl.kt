package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.tima.code.R
import com.tima.code.ResponseBody.CareerTypeBody
import com.tima.code.ResponseBody.CareerTypeResult
import com.tima.code.ResponseBody.ReleasePopData
import com.tima.code.timaconstracts.IReleaseTimePresent
import com.tima.code.timaconstracts.IReleaseTimeView
import com.tima.code.timaconstracts.OnSelectListener
import com.tima.code.timaviewmodels.ReleaseTimeViewModelImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.IDataListener
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.GsonUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/9/12
 *   email : zhijun.li@timanetworks.com
 *
 */
class ReleaseTimePresenterImpl(view: IReleaseTimeView) : IReleaseTimePresent {
    override fun selectRecTime() {


    }

    override fun selectEdu() {
    }

    var mView: IReleaseTimeView? = view
    val popData = arrayListOf<ReleasePopData>()
    val careerTypes = arrayListOf<CareerTypeResult>()
    var selectCareerData: String? = null
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
//        careerType(0)
        popDataSelect()
    }

    private fun careerType(code: Int) {
        Observable.create(ObservableOnSubscribe<ArrayList<CareerTypeResult>> { emitter ->
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
                            emitter.onNext(careerTypes)
                        }
                    }

                    override fun errorData(error: String) {
                        ExceptionDeal.handleException(error)
                    }
                })
            } else {
                emitter.onNext(careerTypes)
            }
        }).subscribe {
            popData.clear()
            it.forEach {
                if (it.parent == code) {
                    popData.add(ReleasePopData(0, "", it.name, it.id, it.parent))
                }
            }
            careerDataDeal()
        }


    }

    fun careerDataDeal() {
        if (popData.isNotEmpty()) {
            mView?.showPop(popData, object : OnSelectListener {
                override fun selected(result: ReleasePopData) {
                    popData.clear()
                    careerTypes.forEach {
                        if (it.parent == result.parentId) {
                            popData.add(ReleasePopData(0, "", it.name, it.id, it.parent))
                        }
                    }
                    if (popData.isNotEmpty()) {
                        mView?.showPop(popData, object : OnSelectListener {
                            override fun selected(result: ReleasePopData) {
                                selectCareerData = result.id.toString()
                            }
                        })
                    } else {
                        selectCareerData = result.id.toString()
                    }
                }
            })
        }
    }

    private fun popDataSelect() {
        val weeks = arrayListOf("周一", "周二", "周三", "周四", "周五", "周六", "周七")
        val dates = arrayListOf("8:00-9:00", "9:00-10:00", "10:00-11:00", "11:00-12:00"
                , "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00",
                "17:00-18:00", "18:00-19:00", "19:00-20:00", "20:00-21:00", "21:00-22:00")
        val currentActivity = ActivityManage.instance.getCurrentActivity()
        currentActivity?.let { activity ->
                val view = LayoutInflater.from(activity).inflate(R.layout.code_layout_pop_flowlayout, null)
                val popTvCancel = view.find(R.id.popTvCancel) as TextView
                val popTvSure = view.find(R.id.popTvSure) as TextView
                val popTvTitle = view.find(R.id.popTvTitle) as TextView
                val popTfl = view.find(R.id.popTfl) as TagFlowLayout
                val popupWindow = PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                        .MATCH_PARENT, true)

                val clickListener = View.OnClickListener { v ->
                    when (v?.id) {
                        R.id.popTvCancel -> {
                            popupWindow.dismiss()
                        }
                        R.id.popTvSure -> {
                            activity.toast(popTfl.selectedList.toString())
                            popupWindow.dismiss()

                        }
                    }
                }
                popTvCancel.setOnClickListener(clickListener)
                popTvSure.setOnClickListener(clickListener)
                popTfl.adapter = object : TagAdapter<String>(dates) {
                    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                        val textView = LayoutInflater.from(activity).inflate(R.layout.code_flowlayout_item, parent, false)
                                as TextView
                        textView.text = t
                        return textView
                    }
                }
                popTfl.setOnSelectListener {
//                    activity.toast(it.toString())
                }

                popupWindow.isFocusable = true
                popupWindow.isOutsideTouchable = true;
                popupWindow.isTouchable = true;
                popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
                popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                popupWindow.showAtLocation((activity as BaseActivity).getRootView(), Gravity
                        .BOTTOM, 0, 0)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewModel.detachView()
        mView == null
    }

}