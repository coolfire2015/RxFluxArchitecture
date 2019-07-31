package com.huyingbao.core.common.web.view

import android.os.Bundle
import android.view.View
import com.huyingbao.core.arch.view.RxSubscriberView
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.base.fragment.BaseCommonFragment
import com.huyingbao.core.common.R
import com.huyingbao.core.common.module.CommonContants
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.common_fragment_web.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
class CommonWebFragment : BaseCommonFragment(), BaseView, RxSubscriberView {
    companion object {
        fun newInstance(url: String): CommonWebFragment {
            return CommonWebFragment().apply {
                arguments = Bundle().apply {
                    putString(CommonContants.Key.WEB_URL, url)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_web
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            if (it.containsKey(CommonContants.Key.WEB_URL)) {
                //和系统webview一样
                val settings = web_content.settings
                //支持Javascript 与js交互
                settings.setJavaScriptEnabled(true)
                //支持通过JS打开新窗口
                settings.javaScriptCanOpenWindowsAutomatically = true
                //设置可以访问文件
                settings.allowFileAccess = true
                //支持缩放
                settings.setSupportZoom(true)
                //设置内置的缩放控件
                settings.builtInZoomControls = true
                //自适应屏幕
                settings.useWideViewPort = true
                //多窗口
                settings.setSupportMultipleWindows(true)
                //设置编码格式
                settings.defaultTextEncodingName = "utf-8"
                settings.setAppCacheEnabled(true)
                settings.domStorageEnabled = true
                settings.setAppCacheMaxSize(Long.MAX_VALUE)
                //缓存模式
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                web_content.webViewClient = WebViewClient()
                web_content.webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(webView: WebView?, progress: Int) {
                        super.onProgressChanged(webView, progress)
                        progress_bar_web.progress = progress
                        if (progress == 100) {
                            progress_bar_web.visibility = View.GONE
                        }
                    }
                }
                web_content.loadUrl(it.getString(CommonContants.Key.WEB_URL))
            }
        }
    }
}
