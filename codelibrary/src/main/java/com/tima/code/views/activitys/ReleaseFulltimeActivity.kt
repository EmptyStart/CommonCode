package com.tima.code.views.activitys

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.RoutePaths
import kotlinx.android.synthetic.main.code_activity_release_fulltime.*
/**
 * @author : zhijun.li on 2018/8/29
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.releasefulltime)
class ReleaseFulltimeActivity : AbstractAddressAndMapActivity() {
    override fun getLayoutId(): Int {

        return R.layout.code_activity_release_fulltime
    }

    override fun inits(savedInstanceState: Bundle?) {
        defaultLoaction()
        defaultMarkerDrag()
        defaultMapClick()
        putScrollView(svCreate)
    }

}