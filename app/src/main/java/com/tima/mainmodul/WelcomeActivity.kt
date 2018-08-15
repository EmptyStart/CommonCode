package com.tima.mainmodul

import android.os.Bundle
import com.tima.common.base.BaseActivity
import org.jetbrains.anko.longToast

class WelcomeActivity : BaseActivity() {
    override fun getLayoutId(): Int =R.layout.activity_tima_welcome

    override fun inits(savedInstanceState: Bundle?) {
    }

}
