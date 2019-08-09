package com.huyingbao.module.common.ui.start.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.common.activity.BaseCommonFragActivity

/**
 * 引导页面，使用standard模式启动
 */
class StartActivity : BaseCommonFragActivity() {
    override fun createFragment(): Fragment? {
        return StartFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        //隐藏ActionBar
        supportActionBar?.hide()
        //全屏
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or// 应用的主体内容占用系统status bar的空间
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or// 应用的主体内容占用系统status bar的空间
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or // 应用的主体内容占navigation bar的空间,同样占用status bar空间
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or// 隐藏navigation bar
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY// 沉浸式响应
        // status bar颜色设置为透明
        window.statusBarColor = Color.TRANSPARENT
    }
}