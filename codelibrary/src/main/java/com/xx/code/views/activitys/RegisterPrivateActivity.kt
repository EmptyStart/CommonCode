package com.xx.code.views.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.model.LatLng
import com.xx.code.R
import com.xx.code.responsebody.Company
import com.xx.code.responsebody.Hr
import com.xx.code.responsebody.LocationBean
import com.xx.code.responsebody.LoginResponseBody
import com.xx.code.timaconstracts.IRegisterPrivateView
import com.xx.code.timapresenter.RegisterPrivatePresentImpl
import com.xx.common.BusEvents.SelectEvent5
import com.xx.common.base.Constant
import com.xx.common.base.RoutePaths
import com.xx.common.utils.CameraUtils
import com.xx.common.utils.ImageLoader
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.code_activity_register_private.*
import kotlinx.android.synthetic.main.code_layout_select_address.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast
import org.litepal.LitePal

/**
 * @author : zhijun.li on 2018/9/4
 *   email :
 *
 */
@Route(path = RoutePaths.registerPrivate)
class RegisterPrivateActivity : AbstractAddressAndMapActivity(), IRegisterPrivateView, View.OnClickListener {
    override fun upData() {
        EventBus.getDefault().postSticky(SelectEvent5(2))
    }

    private var locLatitude : Double= -1.0
    private var locLongitude : Double=-1.0
    val mPresent by lazy(LazyThreadSafetyMode.NONE) { RegisterPrivatePresentImpl(this) }

    override fun onClick(v: View?) {
        mPresent.onClick(v)
    }

    override fun selectImage() {
        CameraUtils.matisseCameraOrAlbum(this, CameraUtils.REQUEST_CODE_CHOOSE)
    }

    override fun getName(): String? {
        val trim = et_name.text.toString().trim()
        if (trim.isEmpty()) {
            return null
        }
        return trim
    }

    override fun getLocationBean(): LocationBean? {
        val pro = resources.getString(R.string.code_register_pro)
        val citys = resources.getString(R.string.code_register_city)
        val county = resources.getString(R.string.code_register_county)
        val tvPro = tv_pro.text.toString()
        val tvCity = tv_city.text.toString()
        val tvCounty = tv_county.text.toString()
        val etAddress = et_address.text.toString()

        if (pro.equals(tvPro) || citys.equals(tvCity) || county.equals(tvCounty) || etAddress.isEmpty()) {
            return null
        }
        if (locLatitude!=-1.0&&locLongitude!=-1.0){
            return LocationBean(locLatitude,locLongitude,"",tvPro,tvCity,tvCounty,etAddress)
        }

        if (selectPosition != -1) {
            return locations.get(selectPosition)
        }

        val aMapLocation = Constant.aMapLocation
        aMapLocation?.apply {
            return LocationBean(latitude, longitude, "", tvPro, tvCity, tvCounty, etAddress)
        }
        return null
    }

    var imageUri: Uri? = null
    override fun headImage(): Uri? {
        return imageUri
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

        return R.layout.code_activity_register_private
    }

    override fun inits(savedInstanceState: Bundle?) {
        tv_inname.text = "工作地址"
        actionbar.setOnRightTextListener(this)
        actionbar.setOnRightImageListener(this)
        ivPicHead.setOnClickListener(this)
        defaultMapClick()
        defaultMarkerDrag()

        val loginBody = LitePal.findFirst(LoginResponseBody::class.java)
        val hr = LitePal.findFirst(Hr::class.java)
        val company = LitePal.findFirst(Company::class.java)
        if (loginBody!=null&&hr!=null&&company!=null) {
            val imgbase = loginBody.img_base
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
            if (!hr.name.isNullOrEmpty()){
                et_name.setText(hr.name)
            }
            if (!company.logo.isNullOrEmpty()) {
                loadImage(imgbase+company.logo, ivPicHead)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraUtils.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val obtainResult = Matisse.obtainResult(data)
            for (uri in obtainResult) {
                imageUri = uri
                setIv(uri,ivPicHead)
            }
        }
    }


}