package com.huyingbao.module.wan.ui.login.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.rxview.BaseRxActivity
import com.huyingbao.module.wan.ui.article.view.ArticleActivity
import com.huyingbao.module.wan.ui.login.action.LoginAction
import com.huyingbao.module.wan.ui.login.store.LoginStore
import dagger.Lazy
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class LoginActivity : BaseRxActivity<LoginStore>() {
    @Inject
    lateinit var mLoginFragmentLazy: Lazy<LoginFragment>

    override fun createFragment(): Fragment {
        return mLoginFragmentLazy.get()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe()
    override fun onRxChanged(rxChange: RxChange) {
        super.onRxChanged(rxChange)
        when (rxChange.tag) {
            LoginAction.LOGIN -> {
                startActivity(Intent(this, ArticleActivity::class.java))
                finish()
            }
        }
    }
}
