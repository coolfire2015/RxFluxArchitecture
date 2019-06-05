package com.huyingbao.module.github.ui.login.view

import android.os.Bundle
import com.huyingbao.core.base.activity.BaseRxNavActivity
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.login.store.LoginStore

/**
 * 登录页面
 *
 * Created by liujunfeng on 2019/1/1.
 */
class LoginActivity : BaseRxNavActivity<LoginStore>() {
    override fun getGraphId(): Int {
        return R.navigation.github_nav_login
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}
}
