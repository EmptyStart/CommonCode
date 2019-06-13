package com.xx.code.views.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.model.LatLng
import com.xx.code.R
import com.xx.code.responsebody.Company
import com.xx.code.responsebody.LocationBean
import com.xx.code.responsebody.LoginResponseBody
import com.xx.code.timaconstracts.IRegisterCreateCompanyView
import com.xx.code.timapresenter.RegisterCreateCompanyPresentImpl
import com.xx.common.BusEvents.SelectEvent5
import com.xx.common.base.Constant
import com.xx.common.base.RoutePaths
import com.xx.common.utils.CameraUtils
import com.xx.common.utils.ImageLoader
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.code_activity_register_create_company.*
import kotlinx.android.synthetic.main.code_layout_select_address.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast
import org.litepal.LitePal

/**
 * @author : lzj on 2018/9/2 17:36
 *         # Email
 */
@Route(path = RoutePaths.createcompany)
class RegisterCreateCompanyActivity : AbstractAddressAndMapActivity(),
        IRegisterCreateCompanyView,View.OnClickListener {
    override fun toUpData() {
        EventBus.getDefault().postSticky(SelectEvent5(2))
        finish()
    }

    private var locLatitude : Double= -1.0
    private var locLongitude : Double=-1.0
    override fun getWebAddress(): String? {

        return et_web.text.toString().trim()
    }

    override fun getIntroduce(): String? {

        return et_introduce.text.toString().trim()
    }

    override fun onClick(v: View?) {
        present.onClick(v)
    }

    private var image3: Uri? = null
    override fun image3(): Uri? {
        return image3
    }

    private var image21: Uri? = null
    override fun image21(): Uri? {
        return image21
    }

    private var image22: Uri? = null
    override fun image22(): Uri? {
        return image22
    }

    private var image23: Uri? = null
    override fun image23(): Uri? {
        return image23
    }

    override fun getStaffes(): TextView {
        return tv_company_size
    }

    private var headImage: Uri? = null
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

        if (locLatitude!=-1.0&&locLongitude!=-1.0){
            return LocationBean(locLatitude,locLongitude,"",tvPro,tvCity,tvCounty,etAddress)
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


        val loginBody = LitePal.findFirst(LoginResponseBody::class.java)
        val company = LitePal.findFirst(Company::class.java)
        val imgBase = loginBody.img_base
        if (loginBody!=null&&company!=null){
            present.isChangeInfo=true
            rlicImage.visibility=View.GONE
            et_name.setText(company.full_name)

            if (!company.img1.isNullOrEmpty()) {
                loadImage(imgBase+company.img1, iv_add_img1)
            }
            if (!company.img2.isNullOrEmpty()) {
                loadImage(imgBase+company.img2, iv_add_img2)
            }
            if (!company.img3.isNullOrEmpty()) {
                loadImage(imgBase+company.img3, iv_add_img3)
            }
            if (!company.logo.isNullOrEmpty()){
                loadImage(imgBase+company.logo, iv_head)
            }

            if(!company.website.isNullOrEmpty()){
                et_web.setText(company.website)
            }

            if(!company.introduction.isNullOrEmpty()){
                et_introduce.setText(company.introduction)
            }
            if (!company.staffs.isNullOrEmpty()){
                present.setStaffes(company.staffs)
            }
            if (!company.region.isNullOrEmpty()&&!company.address.isNullOrEmpty()&&!company.city.isNullOrEmpty()
                    &&!company.province.isNullOrEmpty()&&!(company.latitude.isNullOrEmpty() && company.longitude.isNullOrEmpty())) {
                tv_county.text = company.region
                et_address.setText(company.address)
                tv_city.text = company.city
                tv_pro.text = company.province
                locLatitude = company.latitude.toDouble()
                locLongitude = company.longitude.toDouble()
                setLocation(LatLng(locLatitude, locLongitude))

            }else{
                defaultLoaction()
            }
        }else{
            defaultLoaction()
        }

    }

    private fun loadImage(url: String?,iv : ImageView){
        if (url.isNullOrEmpty()) return
        ImageLoader.load(this,url,iv,R.mipmap.code_register_addphoto)
    }

    private fun setIv(uri: Uri,iv:ImageView){
        runOnUiThread(object :Runnable{
            override fun run() {
                iv.setImageURI(uri)
            }
        })
    }

    override fun upDatePic(picName: String, type: String) {
        val company = LitePal.findFirst(Company::class.java)
        when(type){
            "2-1"->company.img1=picName
            "2-2"->company.img2=picName
            "2-3"->company.img3=picName
            "1"->company.logo=picName
        }
        company.save()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == RESULT_OK) {

            when(requestCode){
                CameraUtils.HEAD_PICTION->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        headImage = uri
                        setIv(uri,iv_head)
                    }
                }
                CameraUtils.IMAGE_21->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image21 = uri
                        setIv(uri,iv_add_img1)
                    }
                }
                CameraUtils.IMAGE_22->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image22 = uri
                        setIv(uri,iv_add_img2)
                    }
                }
                CameraUtils.IMAGE_23->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image23 = uri
                        setIv(uri,iv_add_img3)
                    }
                }
                CameraUtils.IMAGE_LOGO->{
                    val obtainResult = Matisse.obtainResult(data)
                    for (uri in obtainResult) {
                        image3 = uri
                        setIv(uri,iv_add_photo)
                    }
                }
            }
        }
    }


}