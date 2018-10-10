package com.tima.code.views.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tima.code.R
import com.tima.common.alipay.AlipayUtils
import com.tima.common.alipay.PayBackListener
import com.tima.common.alipay.PayOrderListener
import com.tima.common.base.BaseActivity
import com.tima.common.utils.ResourceUtil
import kotlinx.android.synthetic.main.code_activity_put_foward.*
import org.jetbrains.anko.toast

/**
 *  提现--界面
 *  Created by Administrator on 2018/8/31/031.
 */
class PutForwardActivity : BaseActivity(), View.OnClickListener{


    override fun getLayoutId(): Int {
        return R.layout.code_activity_put_foward
    }

    override fun inits(savedInstanceState: Bundle?) {
        ll_alipay.setOnClickListener(this)
        ll_wechat.setOnClickListener(this)
        abv_type4.setOnRightTextListener(this)
        abv_type4.setOnRightImageListener(this)
        tv_alipay_update.setOnClickListener(this)
        tv_wechat_update.setOnClickListener(this)
        tv_full_presentation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ll_alipay->{
                toSelect(0)
            }
            R.id.ll_wechat->{
                toSelect(1)
            }
            R.id.tv_actionbar_right_title->{
                toast("确认提现")

            }
            R.id.iv_actionbar_cancle->{
                finish()
            }
            R.id.tv_alipay_update->{
                var intent = Intent(this,PaymentMethodActivity::class.java)
                intent.putExtra("paymentType",1)
                startActivity(intent)
            }
            R.id.tv_wechat_update->{
                var intent = Intent(this,PaymentMethodActivity::class.java)
                intent.putExtra("paymentType",2)
                startActivity(intent)
            }
            R.id.tv_full_presentation->{
                toast("全部提现")
            }
        }
    }

    fun toSelect(position : Int){
        var white = ResourceUtil.getColorId(R.color.white)
        var gray = ResourceUtil.getColorId(R.color.text_gray)
        ll_alipay.setBackgroundResource(if(position == 0) R.drawable.radius_white_solid_red else R.drawable.radius_white_solid_gray)
        iv_alipay_logo.setImageResource(if(position == 0) R.mipmap.ic_alipay_dark else R.mipmap.ic_alipay_light)
        tv_alipay_name.setTextColor(if(position == 0) white else gray)
        tv_alipay_update.setTextColor(if(position == 0) white else gray)

        ll_wechat.setBackgroundResource(if(position == 1) R.drawable.radius_white_solid_red else R.drawable.radius_white_solid_gray)
        iv_wechat_logo.setImageResource(if(position == 1) R.mipmap.ic_wechat_dark else R.mipmap.ic_wechat)
        tv_wechat_name.setTextColor(if(position == 1) white else gray)
        tv_wechat_update.setTextColor(if(position == 1) white else gray)
    }



}
