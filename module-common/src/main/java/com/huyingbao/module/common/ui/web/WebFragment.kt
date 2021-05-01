//package com.huyingbao.module.common.ui.web
//
//import android.os.Bundle
//import android.view.View
//import com.huyingbao.core.arch.dispatcher.FluxSubscriber
//import com.huyingbao.core.base.BaseView
//import com.huyingbao.core.base.common.fragment.BaseCommonFragment
//import com.huyingbao.module.common.R
//import com.huyingbao.module.common.app.CommonAppConstants
//import com.tencent.smtt.sdk.WebChromeClient
//import com.tencent.smtt.sdk.WebSettings
//import com.tencent.smtt.sdk.WebView
//import com.tencent.smtt.sdk.WebViewClient
//
///**
// * X5内核WebView展示页面，有进度展示，有下拉刷新
// *
// * Created by liujunfeng on 2019/1/1.
// */
//class WebFragment : BaseCommonFragment(), BaseView, FluxSubscriber {
//    companion object {
//        fun newInstance(url: String): WebFragment {
//            return WebFragment().apply {
//                arguments = Bundle().apply {
//                    putString(CommonAppConstants.Key.URL, url)
//                }
//            }
//        }
//    }
//
//    override fun getLayoutId() = R.layout.common_fragment_web
//
//    override fun afterCreate(savedInstanceState: Bundle?) {
//        initWebView()
//        arguments?.getString(CommonAppConstants.Key.URL)?.let { url ->
//            //加载网页
//            web_content.loadUrl(url)
//            //设置网页刷新
//            rfl_content.setOnRefreshListener {
//                web_content.loadUrl(url)
//                rfl_content.finishRefresh()
//            }
//        }
//    }
//
//    /**
//     * 初始化[WebViewClient]
//     */
//    private fun initWebView() {
//        web_content.run {
//            //WebView设置
//            settings.run {
//                //支持Javascript 与js交互
//                setJavaScriptEnabled(true)
//                //支持通过JS打开新窗口
//                javaScriptCanOpenWindowsAutomatically = true
//                //设置可以访问文件
//                allowFileAccess = true
//                //支持缩放
//                setSupportZoom(true)
//                //设置内置的缩放控件
//                builtInZoomControls = true
//                //自适应屏幕
//                useWideViewPort = true
//                //多窗口
//                setSupportMultipleWindows(true)
//                //设置编码格式
//                defaultTextEncodingName = "utf-8"
//                setAppCacheEnabled(true)
//                domStorageEnabled = true
//                setAppCacheMaxSize(Long.MAX_VALUE)
//                //缓存模式
//                cacheMode = WebSettings.LOAD_NO_CACHE
//            }
//            //阻止部分网页跳转到浏览器
//            webViewClient = WebViewClient()
//            //进度监听
//            webChromeClient = object : WebChromeClient() {
//                override fun onProgressChanged(webView: WebView?, progress: Int) {
//                    super.onProgressChanged(webView, progress)
//                    if (this@WebFragment.isResumed && progress_bar_web != null) {
//                        progress_bar_web.progress = progress
//                        if (progress == 100) {
//                            progress_bar_web.visibility = View.GONE
//                        } else {
//                            progress_bar_web.visibility = View.VISIBLE
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
