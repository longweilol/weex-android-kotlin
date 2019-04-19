package com.fby.promotion.Main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import com.fby.promotion.Home.model.ShareMedia
import com.fby.promotion.Main.widget.ShareBottomDialog
import com.fby.promotion.R
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.toolbar_webview.*

/**
 *    author : fby
 *    date   : 2019/4/16
 *    desc   :
 *    version: 1.0
 */
class WebViewActivity : BaseFragmentActivity() {


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setSupportActionBar(tb_toolbar)
        tb_toolbar.setNavigationIcon(R.mipmap.back)
        //显示返回键
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar_title.text = intent.getStringExtra("title")?:""
        val shareMedia = intent.getParcelableExtra<ShareMedia>("shareMedia")

        btn_share.setOnClickListener {
            val dialog = ShareBottomDialog()
            dialog.setShareMedia(1,shareMedia)
            dialog.show(this.supportFragmentManager)
        }
        tb_toolbar.setNavigationOnClickListener(View.OnClickListener { finish() })

        val webSettings : WebSettings= web_view.getSettings()
        // 让WebView能够执行javaScript
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        // 让JavaScript可以自动打开windows
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        // 设置缓存
        webSettings.setAppCacheEnabled(true)
        webSettings.userAgentString = "biwei"
        // 设置缓存模式,一共有四种模式
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true)
        // 将图片调整到合适的大小
        webSettings.useWideViewPort = true
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        // 设置可以被显示的屏幕控制
        webSettings.displayZoomControls = true
        // 设置默认字体大小
        webSettings.defaultFontSize = 12

        val url = intent.getStringExtra("url")
        // 加载需要显示的网页
        web_view.loadUrl(url)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return super.onKeyDown(keyCode, event)
        if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
            if (web_view.canGoBack())
            {
                web_view.goBack() //goBack()表示返回WebView的上一页面
                return true
            }else
            {
                finish()
                return true
            }
        }
        return false
    }

}
