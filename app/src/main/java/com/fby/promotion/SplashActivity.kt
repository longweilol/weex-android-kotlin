package com.fby.promotion

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentActivity
import com.fby.promotion.Main.WeexMainActivity
import com.fby.promotion.Main.common.Constant
import com.fby.promotion.Main.utils.GlobalUtil
import org.jetbrains.anko.startActivity


class SplashActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        GlobalUtil.changStatusIconCollor(this,false)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity<WeexMainActivity>("url" to "index.js?#/main")
            this.finish()
        }, 200)
    }
}