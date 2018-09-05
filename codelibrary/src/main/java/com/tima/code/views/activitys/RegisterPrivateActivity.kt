package com.tima.code.views.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tima.code.R
import com.tima.code.timaconstracts.IRegisterPrivateView
import com.tima.code.timapresenter.RegisterPrivatePresentImpl
import com.tima.common.base.BaseActivity
import com.tima.common.base.RoutePaths
import com.tima.common.utils.CameraUtils
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.code_activity_register_private.*
import org.jetbrains.anko.toast

/**
 * @author : zhijun.li on 2018/9/4
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.registerPrivate)
class RegisterPrivateActivity : BaseActivity(), IRegisterPrivateView ,View.OnClickListener{
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