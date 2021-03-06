package com.xx.code.presenter

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.xx.code.R
import com.xx.code.responsebody.Company
import com.xx.code.responsebody.CompanyStaffsBody
import com.xx.code.responsebody.PicResponse
import com.xx.code.responsebody.Result
import com.xx.code.constracts.IRegisterCreateCompanyPresent
import com.xx.code.constracts.IRegisterCreateCompanyView
import com.xx.code.viewmodels.RegisterCreateCompanyViewModelImpl
import com.xx.common.base.Constant
import com.xx.common.base.IDataFileListener
import com.xx.common.base.IDataListener
import com.xx.common.https.ExceptionDeal
import com.xx.common.utils.CameraUtils
import com.xx.common.utils.FileUtils
import com.xx.common.utils.GsonUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.litepal.LitePal
import java.io.File

/**
 * @author : zhijun.li on 2018/9/5
 *   email :
 *
 */

class RegisterCreateCompanyPresentImpl(mView: IRegisterCreateCompanyView) : IRegisterCreateCompanyPresent {
    override var isChangeInfo: Boolean=false
    var mView: IRegisterCreateCompanyView? = mView
    var selectStaffs: String = ""
    val staffsList = arrayListOf<Result>()
    var count = -1;
    val mViewMode by lazy(LazyThreadSafetyMode.NONE) { RegisterCreateCompanyViewModelImpl() }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_actionbar_right_title -> {
                count = 1
                patchAll()
            }
            R.id.iv_actionbar_cancle -> {
                (view.context as Activity).finish()
            }
            R.id.iv_head -> {
                //这里是LOGO
                mView?.selectImage(CameraUtils.HEAD_PICTION)
            }
            R.id.iv_add_img1 -> {
                mView?.selectImage(CameraUtils.IMAGE_21)
            }
            R.id.iv_add_img2 -> {
                mView?.selectImage(CameraUtils.IMAGE_22)
            }
            R.id.iv_add_img3 -> {
                mView?.selectImage(CameraUtils.IMAGE_23)
            }
            R.id.iv_add_photo -> {
                //这里是营业执照
                mView?.selectImage(CameraUtils.IMAGE_LOGO)
            }
            R.id.rl_company_size -> {
                getConfig()
            }
        }
    }

    fun patchAll() {
        mView?.apply {
            var pairs = mutableMapOf<String, String>()
            val name = getName()
            val locationBean = getLocationBean()
            val headImage = headImage()
            val image21 = image21()
            val image22 = image22()
            val image23 = image23()
            val introduce = getIntroduce()
            val image3 = image3()



            if (name.isNullOrEmpty()) {
                showError("公司名称是必填项")
                return
            }
            if (!isChangeInfo) {
                count++
                if (image3 == null) {
                    showError("公司营业执照是必填项")
                    return
                } else {
                    upPic(image3, "3")
                }
            }
            if (locationBean == null) {
                showError("公司详细地址是必填项")
                return
            }

            if (!introduce.isNullOrEmpty()) {
                pairs.put("introduction", introduce!!)
            }


            if (selectStaffs.isNotEmpty()) {
                pairs.put("staffs", selectStaffs)
            }
            pairs["id"] = Constant.companyId
            pairs["full_name"] = name!!
            pairs["type"] = "1"
            pairs["address"] = locationBean.snippet
            pairs["province"] = locationBean.province
            pairs["city"] = locationBean.city
            pairs["region"] = locationBean.district
            pairs["latitude"] = locationBean.latitude.toString()
            pairs["longitude"] = locationBean.longitude.toString()


            pairs["status"] = "1"

            patchCompany(pairs)

            if (image21 != null) {
                count++
                upPic(image21, "2-1")
            }
            if (image22 != null) {
                count++
                upPic(image22, "2-2")
            }
            if (image23 != null) {
                count++
                upPic(image23, "2-3")
            }

            if (headImage != null) {
                count++
                upPic(headImage, "1")
            }
        }
    }

    private fun saved() {
        mView?.showError("资料提交审核成功！")
        mView?.toUpData()
    }

    private fun upPic(uri: Uri, type: String) {
        mView?.apply {
            val file: File? = FileUtils.uriToFile(uri)
            val name = file?.name
            val ext = FileUtils.picTailName(name) ?: return
            showLoading()
            mViewMode.addOnUpPicListener(object : IDataFileListener {

                override fun successData(success: String) {
                    try {
                        val picResponse = GsonUtils.getGson.fromJson(success, PicResponse::class.java)
                        mView?.upDatePic(picResponse.file,type)
                        if (count == 1) {
                            saved()
                        } else {
                            count--
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                }

                override fun errorData(error: String) {
                    mView?.hideLoading()
                    ExceptionDeal.handleException(error)
                }

                override fun requestType(): MultipartBody.Part? = MultipartBody.Part.createFormData("type", type)

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
                    mView?.hideLoading()
                    ExceptionDeal.handleException(error)
                }
            })
        }
    }

    private fun getConfig() {
        if (staffsList.isEmpty()) {
            mView?.apply {
                showLoading()
                mViewMode.addConfigInfo(object : IDataListener {
                    override fun requestData(): Map<String, String>? {
                        return mapOf(Pair("type", "COMPANY_STAFFS"))

                    }

                    override fun successData(success: String) {
                        mView?.hideLoading()
                        val staffsBody = GsonUtils.getGson.fromJson(success, CompanyStaffsBody::class.java)
                        staffsList.addAll(staffsBody.results)
                        val list = arrayListOf<String>()
                        for (result in staffsList) {
                            list.add(result.value)
                        }
                        mView?.apply {
                            if (list.isEmpty()) {
                                return
                            }
                            MaterialDialog.Builder(getStaffes().context)
                                    .title("请选择公司规模")
                                    .positiveText("确定")
                                    .items(list)
                                    .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                                        override fun onSelection(dialog: MaterialDialog?, itemView: View?, which: Int, text: CharSequence?): Boolean {
                                            val result = staffsList.get(which)
                                            selectStaffs = result.key
                                            getStaffes().text = result.value
                                            return true
                                        }
                                    })
                                    .onPositive(object : MaterialDialog.SingleButtonCallback {
                                        override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                                            dialog.dismiss()
                                        }

                                    }).show()
                        }
                    }

                    override fun errorData(error: String) {
                        mView?.hideLoading()
                        ExceptionDeal.handleException(error)
                    }
                })
            }
        }else{
            val list = arrayListOf<String>()
            for (result in staffsList) {
                list.add(result.value)
            }
            mView?.apply {
                if (list.isEmpty()) {
                    return
                }
                MaterialDialog.Builder(getStaffes().context)
                        .title("请选择公司规模")
                        .positiveText("确定")
                        .items(list)
                        .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                            override fun onSelection(dialog: MaterialDialog?, itemView: View?, which: Int, text: CharSequence?): Boolean {
                                val result = staffsList.get(which)
                                selectStaffs = result.key
                                getStaffes().text = result.value
                                return true
                            }
                        })
                        .onPositive(object : MaterialDialog.SingleButtonCallback {
                            override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                                dialog.dismiss()
                            }

                        }).show()
            }
        }
    }
    override fun setStaffes(key: String?) {
        if (staffsList.isEmpty()) {
            mView?.apply {
                showLoading()
                mViewMode.addConfigInfo(object : IDataListener {
                    override fun requestData(): Map<String, String>? {
                        return mapOf(Pair("type", "COMPANY_STAFFS"))

                    }

                    override fun successData(success: String) {
                        mView?.hideLoading()
                        val staffsBody = GsonUtils.getGson.fromJson(success, CompanyStaffsBody::class.java)
                        staffsList.addAll(staffsBody.results)

                        for (result in staffsList) {
                            if (TextUtils.equals(key,result.key)){
                                mView?.getStaffes()?.text=result.value
                            }
                        }
                    }

                    override fun errorData(error: String) {
                        mView?.hideLoading()
                        ExceptionDeal.handleException(error)
                    }
                })
            }
        }else{
            for (result in staffsList) {
                if (TextUtils.equals(key,result.key)){
                    mView?.getStaffes()?.text=result.value
                }
            }
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
                    LitePal.deleteAll(Company::class.java)
                    company.save()
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