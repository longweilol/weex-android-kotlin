package com.fby.promotion.Main

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.fby.promotion.Main.common.Constant
import com.fby.promotion.R
import com.taobao.weex.IWXRenderListener
import com.taobao.weex.WXSDKInstance
import com.taobao.weex.common.WXRenderStrategy
import kotlinx.android.synthetic.main.activity_weex_main.*
import org.jetbrains.anko.toast

/**
 *    author : fby
 *    date   : 2019/4/1617:45
 *    desc   :
 *    version: 1.0
 */
class WeexActivity :  BaseActivity(), IWXRenderListener {
    lateinit var mWeexInstance : WXSDKInstance

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weex_main)
        mWeexInstance = WXSDKInstance(this)
        mWeexInstance.registerRenderListener(this)
        mWeexInstance.renderByUrl("weex",Constant.weexBaseUrl+intent.getStringExtra("url"),null,null,WXRenderStrategy.APPEND_ASYNC)
    }

    override fun onViewCreated(instance: WXSDKInstance?, view: View?) {
        frame_weex.addView(view)
    }

    override fun onRefreshSuccess(instance: WXSDKInstance?, width: Int, height: Int) {
    }

    override fun onException(instance: WXSDKInstance?, errCode: String?, msg: String?) {
    }

    override fun onRenderSuccess(instance: WXSDKInstance?, width: Int, height: Int) {
    }

    override fun onStart() {
        super.onStart()
        if (mWeexInstance != null) {
            mWeexInstance.onActivityStart()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mWeexInstance != null) {
            mWeexInstance.onActivityResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mWeexInstance.onActivityPause()
    }

    override fun onStop() {
        super.onStop()
        mWeexInstance.onActivityStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mWeexInstance.onActivityDestroy()
    }
}
