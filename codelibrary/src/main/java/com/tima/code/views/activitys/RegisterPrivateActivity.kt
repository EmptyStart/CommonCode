package com.tima.code.views.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.ResponseBody.LocationBean
import com.tima.code.timaconstracts.IRegisterPrivateView
import com.tima.code.timapresenter.RegisterPrivatePresentImpl
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.CameraUtils
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.code_activity_register_private.*
import kotlinx.android.synthetic.main.code_layout_select_address.*
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/9/4
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.registerPrivate)
class RegisterPrivateActivity : AbstractAddressAndMapActivity(), IRegisterPrivateView, View.OnClickListener {


    val mPresent by lazy(LazyThreadSafetyMode.NONE) { RegisterPrivatePresentImpl(this) }

    override fun onClick(v: View?) {
        mPresent.onClick(v)
    }

    override fun selectImage() {
        CameraUtils.matisseCameraOrAlbum(this, CameraUtils.REQUEST_CODE_CHOOSE)
    }

    override fun getName(): String? {
        val trim = et_name.text.toString().trim()
        if (trim.isEmpty()){
            toast("请输入姓名！")
            return null
        }
        return trim
    }

    override fun getLocationBean(): LocationBean? {
        val pro = resources.getString(R.string.code_register_pro)
        val citys = resources.getString(R.string.code_register_city)
        val county = resources.getString(R.string.code_register_county)
        if (pro.equals(tv_pro.text.toString())||citys.equals(tv_city.text.toString())||county.equals(tv_county.text.toString())){
            toast("请输入地址!")
            return null
        }
        if (selectPosition!=-1){
            return locations.get(selectPosition)
        }
        val aMapLocation = Constant.aMapLocation
        aMapLocation?.apply {
            return LocationBean(latitude,longitude,address,province,city,district,street+streetNum+poiName)
        }
        return null
    }

    var imageUri: Uri? = null
    override fun headImage(): Uri? {
        return imageUri
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }


    override fun getLayoutId(): Int {

        return R.layout.code_activity_register_private
    }

    override fun inits(savedInstanceState: Bundle?) {
        tv_inname.text="工作地址"
        actionbar.setOnRightTextListener(this)
        ivPicHead.setOnClickListener(this)
        defaultLoaction()
        defaultMarkerDrag()
        defaultMapClick()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraUtils.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val obtainResult = Matisse.obtainResult(data)
            for (uri in obtainResult) {
                imageUri = uri
                ivPicHead.setImageURI(uri)
            }
        }
    }


}