package com.tima.code.views.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.code.ResponseBody.LocationBean
import com.tima.code.timaconstracts.IRegisterCreateCompanyView
import com.tima.code.timapresenter.RegisterCreateCompanyPresentImpl
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.CameraUtils
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.code_activity_register_create_company.*
import kotlinx.android.synthetic.main.code_layout_select_address.*
import org.jetbrains.anko.toast

/**
 * @author : lzj on 2018/9/2 17:36
 *         # Email
 */
@Route(path = RoutePaths.createcompany)
class RegisterCreateCompanyActivity : AbstractAddressAndMapActivity(),
        IRegisterCreateCompanyView,View.OnClickListener {
    override fun getWebAddress(): String? {

        return et_web.text.toString().trim()
    }

    override fun getIntroduce(): String? {

        return et_introduce.text.toString().trim()
    }

    override fun onClick(v: View?) {
        present.onClick(v)
    }

    var image3: Uri? = null
    override fun image3(): Uri? {
        return image3
    }

    var image21: Uri? = null
    override fun image21(): Uri? {
        return image21
    }

    var image22: Uri? = null
    override fun image22(): Uri? {
        return image22
    }

    var image23: Uri? = null
    override fun image23(): Uri? {
        return image23
    }

    override fun getStaffes(): TextView {
        return tv_company_size
    }

    var headImage: Uri? = null
    override fun headImage(): Uri? {
        return headImage
    }

    override fun selectImage(code: Int) {
        CameraUtils.matisseCameraOrAlbum(this, code)
    }

    override fun getName(): String? {
        return et_name.text.toString().trim()
    }

    override fun getLocationBean(): LocationBean? {
        val pro = resources.getString(R.string.code_register_pro)
        val citys = resources.getString(R.string.code_register_city)
        val county = resources.getString(R.string.code_register_county)
        val tvPro = tv_pro.text.toString()
        val tvCity = tv_city.text.toString()
        val tvCounty = tv_county.text.toString()
        val etAddress = et_address.text.toString()

        if (pro.equals(tvPro)||citys.equals(tvCity)||county.equals(tvCounty)||etAddress.isEmpty()){
            return null
        }
        if (selectPosition!=-1){
            return locations.get(selectPosition)
        }
        val aMapLocation = Constant.aMapLocation
        aMapLocation?.apply {
            return LocationBean(latitude,longitude,"",tvPro,tvCity,tvCounty,etAddress)
        }
        return null
    }

    override fun showLoading() {
        loadingBar.show()
    }

    override fun hideLoading() {
        loadingBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun getLayoutId(): Int {
        return R.layout.code_activity_register_create_company
    }
    private val present by lazy(LazyThreadSafetyMode.NONE){
        RegisterCreateCompanyPresentImpl(this)
    }
    override fun inits(savedInstanceState: Bundle?) {
        lifecycle.addObserver(present)
        putScrollView(svCreate)
        defaultLoaction()
        defaultMarkerDrag()
        defaultMapClick()
        actionbar.setOnRightImageListener(this)
        actionbar.setOnRightTextListener(this)
        rl_company_size.setOnClickListener(this)
        iv_add_img1.setOnClickListener(this)
        iv_add_img2.setOnClickListener(this)
        iv_add_img3.setOnClickListener(this)
        iv_add_photo.setOnClickListener(this)
        iv_head.setOnClickListener(this)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == RESULT_OK) {

            when(requestCode){
                CameraUtils.HEAD_PICTION->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        headImage = uri
                        iv_head.setImageURI(uri)
                    }
                }
                CameraUtils.IMAGE_21->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image21 = uri
                        iv_add_img1.setImageURI(uri)
                    }
                }
                CameraUtils.IMAGE_22->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image22 = uri
                        iv_add_img2.setImageURI(uri)
                    }
                }
                CameraUtils.IMAGE_23->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image23 = uri
                        iv_add_img3.setImageURI(uri)
                    }
                }
                CameraUtils.IMAGE_LOGO->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image3 = uri
                        iv_add_photo.setImageURI(uri)
                    }
                }
            }
        }
    }


}