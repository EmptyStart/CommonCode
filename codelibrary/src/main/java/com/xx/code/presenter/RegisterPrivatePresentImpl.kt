package com.xx.code.presenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.net.Uri
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.xx.code.R
import com.xx.code.responsebody.Company
import com.xx.code.responsebody.Hr
import com.xx.code.responsebody.NewHr
import com.xx.code.constracts.IRegisterPrivatePresent
import com.xx.code.constracts.IRegisterPrivateView
import com.xx.code.viewmodels.RegisterPrivateViewModelImpl
import com.xx.common.base.Constant
import com.xx.common.base.IDataFileListener
import com.xx.common.base.IDataListener
import com.xx.common.base.RoutePaths
import com.xx.common.https.ExceptionDeal
import com.xx.common.utils.ActivityManage
import com.xx.common.utils.FileUtils
import com.xx.common.utils.GsonUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.litepal.LitePal

/**
 * @author : zhijun.li on 2018/9/5
 *   email :
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
            R.id.iv_actionbar_cancle -> {
                (view.context as Activity).finish()
            }
        }
    }

    private fun patchAll() {
        mView?.apply {
            val pairs = mutableMapOf<String, String>()
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
                count ++
                upPic(headImage)
            }
            patchHr(name!!)
            pairs.put("id",Constant.companyId)
            pairs.put("type", "0")
            pairs.put("address", locationBean.snippet)
            pairs.put("province", locationBean.province)
            pairs.put("city", locationBean.city)
            pairs.put("region", locationBean.district)
            pairs.put("latitude", locationBean.latitude.toString())
            pairs.put("longitude", locationBean.longitude.toString())

            pairs["status"]="1"

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
            showLoading()
            val file = FileUtils.uriToFile(headImage) ?: return
            val name = file.name
            val ext = FileUtils.picTailName(name) ?: return
            mViewMode.addOnUpPicListener(object : IDataFileListener {

                override fun successData(success: String) {
                    mView?.hideLoading()
                    if (count == 1) {
                        saved()
                    } else {
                        count--

                    }
                }

                override fun errorData(error: String) {
                    mView?.hideLoading()
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
            showLoading()
            mViewMode.addOnUpHrListener(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return mapOf(Pair("name", name))
                }

                override fun successData(success: String) {
                    val newHr = GsonUtils.getGson.fromJson(success, NewHr::class.java)
                    val hr = LitePal.findFirst(Hr::class.java)
                    hr.name=newHr.name
                    hr.save()
                    mView?.upData()
                    mView?.hideLoading()
                    if (count == 1) {
                        saved()
                    } else {
                        count--

                    }
                }

                override fun errorData(error: String) {
                    mView?.hideLoading()
                    ExceptionDeal.handleException(error)
                }
            })
        }
    }

    private fun patchCompany(requestMap: Map<String, String>) {
        mView?.apply {
            showLoading()
            mViewMode.addOnUpCompanyListener(object : IDataListener {
                override fun requestData(): Map<String, String>? {
                    return requestMap

                }

                override fun successData(success: String) {
                    val company = GsonUtils.getGson.fromJson(success, Company::class.java)
                    company.saveOrUpdateAsync("id=?",company.id.toString())
                    mView?.hideLoading()
                    if (count == 1) {
                        saved()
                    } else {
                        count--
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