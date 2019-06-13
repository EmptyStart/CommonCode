package com.xx.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.code.timaconstracts.IRegisterCompanyPresent
import com.xx.code.timaconstracts.IRegisterCompanyView
import com.xx.code.timaviewmodels.RegisterCompanyViewModelImpl
import com.xx.common.base.IDataFileListener
import com.xx.common.base.IDataListener
import com.xx.common.base.RoutePaths
import com.xx.common.https.ExceptionDeal
import com.xx.common.utils.ActivityManage
import com.xx.common.utils.FileUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author : zhijun.li on 2018/9/5
 *   email :
 *
 */
class RegisterCompanyPresentImpl(mView: IRegisterCompanyView) : IRegisterCompanyPresent {
    var mView: IRegisterCompanyView? = mView
    var count = 2;
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { RegisterCompanyViewModelImpl() }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_actionbar_right_title -> {
                count = 2
                upPic()
                patchHr()
            }
            R.id.ivPicHead -> {
                mView?.selectImage(201)
            }
        }
    }

    private fun saved() {
        ARouter.getInstance().build(RoutePaths.mainpage).navigation()
        val activity = ActivityManage.instance.getCurrentActivity()
        activity?.finish()
    }

    private fun upPic() {
        mView?.apply {
            val headImage = headImage() ?: return
            val file = FileUtils.uriToFile(headImage) ?: return
            val name = file.name
            val ext = FileUtils.picTailName(name) ?: return
            showLoading()
            mViewMode.addOnUpPicListener(object : IDataFileListener {

                override fun successData(success: String) {
                    mView?.hideLoading()
                    if (count == 2) {
                        count--
                    } else {
                        saved()
                    }
                }

                override fun errorData(error: String) {
                    mView?.hideLoading()
                    ExceptionDeal.handleException(error)
                }

                override fun requestType(): MultipartBody.Part? = MultipartBody.Part
                        .createFormData("type", "3")

                override fun requestExt(): MultipartBody.Part? = MultipartBody.Part.createFormData("ext", ext)

                override fun requestFileData(): MultipartBody.Part {
                    val part = MultipartBody.Part.createFormData("img_file", name, RequestBody
                            .create(MediaType.parse("multipart/form-data"), file))
                    return part
                }

            })
        }

    }

    private fun patchHr() {
        mView?.apply {
            val name = getName()
            val locationBean = getLocationBean()
            if (name.isNullOrEmpty()) {
                return
            }
            if (locationBean == null) {
                return
            }
            showLoading()
            mViewMode.addOnUpHrListener(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return mapOf(Pair("name", name!!), Pair("address", locationBean.snippet), Pair("address", locationBean.snippet)
                            , Pair("province", locationBean.province), Pair("city", locationBean.city), Pair("region", locationBean.district))
                }

                override fun successData(success: String) {
                    mView?.hideLoading()
                    if (count == 2) {
                        count--
                    } else {
                        saved()
                    }
                }

                override fun errorData(error: String) {
                    mView?.hideLoading()
                    ExceptionDeal.handleException(error)
                }
            })
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        mViewMode.detachView()
        mView == null
    }
}