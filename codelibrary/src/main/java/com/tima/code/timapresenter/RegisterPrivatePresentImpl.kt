package com.tima.code.timapresenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.code.timaconstracts.IRegisterPrivatePresent
import com.tima.code.timaconstracts.IRegisterPrivateView
import com.tima.code.timaviewmodels.RegisterPrivateViewModelImpl
import com.tima.common.base.IDataFileListener
import com.tima.common.base.IDataListener
import com.tima.common.base.RoutePaths
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.ActivityManage
import com.tima.common.utils.FileUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author : zhijun.li on 2018/9/5
 *   email : zhijun.li@timanetworks.com
 *
 */
class RegisterPrivatePresentImpl(mView: IRegisterPrivateView) : IRegisterPrivatePresent {
    var mView: IRegisterPrivateView? = mView
    var count = 2;
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { RegisterPrivateViewModelImpl() }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_actionbar_right_title -> {
                count = 2
                upPic()
                patchHr()
            }
            R.id.ivPicHead -> {
                mView?.selectImage()
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
            mViewMode.addOnUpPicListener(object : IDataFileListener {

                override fun successData(success: String) {
                    if (count == 2) {
                        count--
                    } else {
                        saved()
                    }
                }

                override fun errorData(error: String) {
                    ExceptionDeal.handleException(error)
                }

                override fun requestType(): MultipartBody.Part? = MultipartBody.Part.createFormData("type", "1")

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
            mViewMode.addOnUpHrListener(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return mapOf(Pair("name", name!!), Pair("address", locationBean.snippet), Pair("address", locationBean.snippet)
                            , Pair("province", locationBean.province), Pair("city", locationBean.city), Pair("region", locationBean.district))
                }

                override fun successData(success: String) {
                    if (count == 2) {
                        count--
                    } else {
                        saved()
                    }
                }

                override fun errorData(error: String) {
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