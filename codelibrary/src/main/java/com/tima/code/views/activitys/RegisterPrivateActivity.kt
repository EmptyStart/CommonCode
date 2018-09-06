package com.tima.code.views.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.contrarywind.adapter.WheelAdapter
import com.contrarywind.listener.OnItemSelectedListener
import com.tima.code.R
import com.tima.code.timaconstracts.IRegisterPrivateView
import com.tima.code.timapresenter.RegisterPrivatePresentImpl
import com.tima.code.views.adapter.ArrayWheelAdapter
import com.tima.common.base.BaseActivity
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.CameraUtils
import com.tima.common.utils.IAMapLocationSuccessListener
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
class RegisterPrivateActivity : BaseActivity(), IRegisterPrivateView ,View.OnClickListener{
    override fun location(listener: IAMapLocationSuccessListener) {

    }

    override fun onClick(v: View?) {
        mPresent.onClick(v)
    }

    override fun selectImage() {
        CameraUtils.matisseCameraOrAlbum(this, CameraUtils.REQUEST_CODE_CHOOSE)
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

    val mPresent by lazy(LazyThreadSafetyMode.NONE) { RegisterPrivatePresentImpl(this) }
    override fun getLayoutId(): Int {

        return R.layout.code_activity_register_private
    }

    override fun inits(savedInstanceState: Bundle?) {
        actionbar.setOnRightTextListener(this)
        ivPicHead.setOnClickListener(this)
//        tv_select_address.text=Constant.aMapLocation?.address
//        if (Constant.aMapLocation==null){
//
//        }
        wv_select.setCyclic(false)
        val list = arrayListOf("item1", "item2", "item3")
        wv_select.adapter=ArrayWheelAdapter<String>(list)
        wv_select.setOnItemSelectedListener { position->{
            toast(list.get(position))
        }}
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraUtils.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val obtainResult = Matisse.obtainResult(data)
            for (uri in obtainResult) {
                imageUri=uri
                ivPicHead.setImageURI(uri)
            }
        }
    }


}