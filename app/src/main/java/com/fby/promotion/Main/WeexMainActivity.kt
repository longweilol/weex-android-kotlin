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
class WeexMainActivity :  BaseActivity(), IWXRenderListener {
    private var exitTime: Long = 0
    lateinit var mWeexInstance : WXSDKInstance

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weex_main)
        mWeexInstance = WXSDKInstance(this)
        mWeexInstance.registerRenderListener(this)
        mWeexInstance.renderByUrl("weexMain",Constant.weexBaseUrl+intent.getStringExtra("url"),null,null,WXRenderStrategy.APPEND_ASYNC)
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
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            this.toast("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            finish()
            System.exit(0)
        }
    }
}
