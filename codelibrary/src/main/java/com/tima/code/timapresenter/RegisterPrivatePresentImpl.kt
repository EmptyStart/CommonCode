package com.tima.code.timapresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.net.Uri
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
                patchAll()
            }
            R.id.ivPicHead -> {
                mView?.selectImage()
            }
        }
    }

    private fun patchAll() {
        mView?.apply {
            val pairs = mapOf<String, String>()
            val headImage = headImage()
            val name = getName()
            val locationBean = getLocationBean()
            if (name.isNullOrEmpty()) {
                showError("请输入姓名")
                return
            }
            if (locationBean == null) {
                showError("请输入工作详细地址")
                return
            }
            if (headImage != null) {
                count = 3
                upPic(headImage)
            }
            patchHr(name!!)
            pairs.plus(Pair("id", "42"))
            pairs.plus(Pair("type", "0"))
            pairs.plus(Pair("address", locationBean.snippet))
            pairs.plus(Pair("province", locationBean.province))
            pairs.plus(Pair("city", locationBean.city))
            pairs.plus(Pair("region", locationBean.district))
            patchCompany(pairs)
        }


    }

    private fun saved() {
        ARouter.getInstance().build(RoutePaths.mainpage).navigation()
        val activity = ActivityManage.instance.getCurrentActivity()
        activity?.finish()
    }

    private fun upPic(headImage: Uri) {
        mView?.apply {
            val file = FileUtils.uriToFile(headImage) ?: return
            val name = file.name
            val ext = FileUtils.picTailName(name) ?: return
            mViewMode.addOnUpPicListener(object : IDataFileListener {

                override fun successData(success: String) {
                    if (count == 1) {
                        saved()
                    } else {
                        count--

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

    private fun patchHr(name: String) {
        mView?.apply {

            mViewMode.addOnUpHrListener(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return mapOf(Pair("name", name))
                }

                override fun successData(success: String) {
                    if (count == 1) {
                        saved()
                    } else {
                        count--

                    }
                }

                override fun errorData(error: String) {
                    ExceptionDeal.handleException(error)
                }
            })
        }
    }

    private fun patchCompany(requestMap: Map<String, String>) {
        mView?.apply {

            mViewMode.addOnUpCompanyListener(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return requestMap

                }

                override fun successData(success: String) {
                    if (count == 1) {
                        saved()
                    } else {
                        count--
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