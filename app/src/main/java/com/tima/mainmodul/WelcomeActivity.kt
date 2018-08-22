package com.tima.mainmodul

import android.os.Bundle
import com.tima.common.base.BaseActivity
import com.tima.common.https.BaseSubscriber
import com.tima.common.https.RetrofitHelper
import com.tima.common.rx.SchedulerUtils
import com.tima.common.utils.LogUtils
import org.jetbrains.anko.doAsync
import kotlinx.android.synthetic.main.activity_tima_welcome.*

class WelcomeActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_tima_welcome

    override fun inits(savedInstanceState: Bundle?) {
        LogUtils.i(this.localClassName,"我打印第一个log")
        tv_welcom.text="ddddd"
        doAsync {
            val map= mapOf<String,String>("type" to "2","menuIds"
                    to "23")
            var map2= mapOf<String,String>("sign" to "b55b88b6e7be2a2b91e9edca175a5a9f","sysType"
                    to "1","appKey" to "SUPERAPP","currentVersion" to "3.2.1.1","timestamp" to
                    "1534819131282"
                    ,"token" to "st_3080f721612143b7ab275d45af45b78a"
            )

//            RetrofitHelper.service.executeGet("99",map)
//                    .compose(SchedulerUtils.ioToMain()).subscribe(BaseSubscriber());
//            RetrofitHelper.service.executePost(map,map2).compose(SchedulerUtils.ioToMain())
//                    .subscribe(BaseSubscriber())
        }
    }

}
