package com.huyingbao.module.github.ui.start.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.module.github.ui.main.view.MainActivity
import com.huyingbao.module.github.ui.start.action.StartAction
import com.huyingbao.module.github.ui.start.store.StartStore
import org.greenrobot.eventbus.Subscribe

/**
 * 引导页面，使用standard模式启动
 */
@Route(path = CommonContants.Address.StartActivity)
class StartActivity : BaseRxFragActivity<StartStore>() {
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

    /**
     * 跳转登录页面
     */
    @Subscribe(sticky = true, tags = [StartAction.TO_LOGIN])
    fun toLogin(rxChange: RxChange) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    /**
     * 跳转主页面
     */
    @Subscribe(sticky = true, tags = [StartAction.TO_MAIN])
    fun toMain(rxChange: RxChange) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
