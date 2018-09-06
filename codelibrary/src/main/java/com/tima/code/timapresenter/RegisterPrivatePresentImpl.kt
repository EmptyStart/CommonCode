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
import com.tima.common.base.RoutePaths
import com.tima.common.https.ExceptionDeal
import com.tima.common.utils.FileUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI

/**
 * @author : zhijun.li on 2018/9/5
 *   email : zhijun.li@timanetworks.com
 *
 */
class RegisterPrivatePresentImpl(mView: IRegisterPrivateView) : IRegisterPrivatePresent {
    var mView: IRegisterPrivateView? = mView
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { RegisterPrivateViewModelImpl() }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_actionbar_right_title -> {
//                ARouter.getInstance().build(RoutePaths.mainpage).navigation()
//                val activity = view.context as? Activity
//                activity?.finish()
                upPic()
            }
            R.id.ivPicHead -> {
                mView?.selectImage()
            }
        }
    }

    private fun upPic() {
        mView?.apply {
            val headImage = headImage() ?: return
            val file = FileUtils.uriToFile(headImage) ?: return
            val name = file.name
            val ext = FileUtils.picTailName(name) ?: return
            mViewMode.addOnUpPicListener(object : IDataFileListener {


                override fun successData(success: String) {


                }

                override fun errorData(error: String) {
                    ExceptionDeal.handleException(error)
                }

                override fun requestType(): Int? = 1

                override fun requestExt(): MultipartBody.Part? = MultipartBody.Part.createFormData("ext", ext)

                override fun requestFileData(): MultipartBody.Part {
                    val part = MultipartBody.Part.createFormData("img_file", name, RequestBody
                            .create(MediaType.parse("multipart/form-data"), file))

                    return part
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