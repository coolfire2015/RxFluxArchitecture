package com.huyingbao.module.wan.kotlin.ui.login.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.rxview.BaseRxActivity
import com.huyingbao.module.wan.kotlin.ui.article.view.ArticleActivity
import com.huyingbao.module.wan.kotlin.ui.login.action.LoginAction
import com.huyingbao.module.wan.kotlin.ui.login.store.LoginStore
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liujunfeng on 2019/1/1.
 */
class LoginActivity : BaseRxActivity<LoginStore>() {
    override fun createFragment(): Fragment {
        return LoginFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(tags = [LoginAction.LOGIN], sticky = true)
    fun onLogin(rxChange: RxChange) {
        startActivity(Intent(this, ArticleActivity::class.java))
        finish()
    }
}
