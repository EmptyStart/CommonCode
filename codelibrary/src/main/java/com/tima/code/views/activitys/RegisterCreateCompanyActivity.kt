package com.tima.code.views.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tima.code.R
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_register_create_company.*

/**
 * @author : lzj on 2018/9/2 17:36
 *         # Email
 */
@Route(path = RoutePaths.createcompany)
class RegisterCreateCompanyActivity : AbstractAddressAndMapActivity() {

    override fun getLayoutId(): Int {
        return R.layout.code_activity_register_create_company
    }

    override fun inits(savedInstanceState: Bundle?) {
        putScrollView(svCreate)
        defaultLoaction()
        defaultMarkerDrag()
        actionbar.setOnRightImageListener(View.OnClickListener {
            finish()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}