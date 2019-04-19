package com.fby.promotion

import android.app.Application
import com.fby.promotion.Main.adapter.ImageAdapter
import com.fby.promotion.Main.adapter.WXHttpsAdapter
import com.fby.promotion.Main.utils.GlobalUtil
import com.fby.promotion.Main.wxcomponent.RichText
import com.fby.promotion.Main.wxmodule.Module
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowLog
import com.raizlabs.android.dbflow.config.FlowManager
import com.taobao.weex.InitConfig
import com.taobao.weex.WXSDKEngine
import me.shaohui.shareutil.ShareConfig
import me.shaohui.shareutil.ShareManager

/**
 *    author : fby
 *    date   : 2019/4/16
 *    desc   :
 *    version: 1.0
 */
class  App:Application(){

    companion object {
        @JvmStatic lateinit var application: App
        private  set
    }

    override fun onCreate() {
        super.onCreate()
        application=this
        //初始化weex
        initWeex()
        //初始化DBFlow
        FlowManager.init(FlowConfig.Builder(this).build())
        //设置日志显示
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V)
        initShareConfig()
    }


    private fun initWeex() {
        var builder : InitConfig.Builder = InitConfig.Builder()
        builder.setImgAdapter(ImageAdapter())
        builder.setHttpAdapter(WXHttpsAdapter())
        var initconfig : InitConfig = builder.build()
        WXSDKEngine.initialize(this,initconfig)
        WXSDKEngine.registerComponent("richText", RichText::class.java)
        WXSDKEngine.registerModule("event", Module::class.java)

    }

    private fun initShareConfig(){

        // 初始化shareUtil
        val config = ShareConfig.instance()
                .qqId("1107515849")
                .weiboId("372953038")
                .wxId("wx2e2e057b0198666c")
        ShareManager.init(config)
    }

}