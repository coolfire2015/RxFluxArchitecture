package com.huyingbao.module.github.ui.login.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.main.view.MainActivity
import com.huyingbao.module.github.ui.start.action.StartActionCreator
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * 登录页面，使用singleTask模式启动，在登录失效，跳转该页面时，清空ActivityTask中的Activity
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonContants.Address.LoginActivity)
class LoginActivity : BaseRxFragActivity<LoginStore>() {
    @Inject
    lateinit var startActionCreator: StartActionCreator

    override fun createFragment(): Fragment? {
        return LoginFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }

    /**
     * 登录成功，跳转主页面
     */
    @Subscribe(tags = [LoginAction.LOGIN], sticky = true)
    fun onLogin(rxAction: RxAction) {
        startActionCreator.getLoginUserInfo()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
