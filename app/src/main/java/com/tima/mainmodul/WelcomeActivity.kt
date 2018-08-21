package com.tima.mainmodul

import android.os.Bundle
import com.tima.common.base.BaseActivity
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils
import com.tima.common.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast

class WelcomeActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_tima_welcome

    override fun inits(savedInstanceState: Bundle?) {
        LogUtils.i(this.localClassName,"我打印第一个log")

        doAsync {
            var map= mapOf<String,String>("sign" to "ea3e71ce9819771fe6b593011f7a6157","appKey"
                    to "SUPERAPP","sysType" to "1","currentVersion" to "3.2.1.1","timestamp" to "1534749048740"
            ,"token" to "st_56adb192b3754b72a85f9a82e581ddad")

            RetrofitHelper.service.executeGet("99",map)
                    .compose(SchedulerUtils.ioToMain()).subscribe(BaseSubscriber());
        }
    }

}
