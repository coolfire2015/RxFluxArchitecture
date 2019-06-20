package com.huyingbao.module.github.ui.login.view

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.base.activity.BaseRxNavActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.main.view.MainActivity
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * 登录页面
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonContants.Address.LoginActivity)
class LoginActivity : BaseRxNavActivity<LoginStore>() {
    @Inject
    lateinit var githubAppStore: GithubAppStore

    override fun getGraphId(): Int {
        return R.navigation.github_nav_login
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }

    /**
     * 获取当前登录用户信息成功，跳转主页面
     */
    @Subscribe(sticky = true, tags = [LoginAction.GET_LOGIN_USER_INFO])
    fun toMainActivity(rxAction: RxAction) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
