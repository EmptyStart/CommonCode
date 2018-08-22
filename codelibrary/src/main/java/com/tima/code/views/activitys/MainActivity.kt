package com.tima.code.views.activitys

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tima.code.R
import com.tima.common.base.BaseActivity
import com.tima.common.base.Constant
import com.tima.common.base.RoutePaths
import com.tima.common.utils.SpHelper

/**
 * @author : zhijun.li on 2018/8/22
 *   email : zhijun.li@timanetworks.com
 *
 */
@Route(path = RoutePaths.main)
class MainActivity : BaseActivity(){

    var office : Int=0;


    override fun getLayoutId(): Int {

        return R.layout.code_activity_main
    }

    override fun inits(savedInstanceState: Bundle?) {
        office=Constant.getPosition()




    }



}