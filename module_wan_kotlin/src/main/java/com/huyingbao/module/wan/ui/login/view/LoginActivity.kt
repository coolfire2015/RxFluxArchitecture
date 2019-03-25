package com.huyingbao.module.wan.ui.login.view

import android.content.Intent
import android.os.Bundle

import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.common.rxview.CommonRxActivity
import com.huyingbao.module.wan.ui.article.view.ArticleActivity
import com.huyingbao.module.wan.ui.login.action.LoginAction
import com.huyingbao.module.wan.ui.login.store.LoginStore

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import javax.inject.Inject
import androidx.fragment.app.Fragment
import dagger.Lazy

/**
 * Created by liujunfeng on 2019/1/1.
 */
class LoginActivity : CommonRxActivity<LoginStore>() {
    @Inject
    internal var mLoginFragmentLazy: Lazy<LoginFragment>? = null

    override fun createFragment(): Fragment {
        return mLoginFragmentLazy!!.get()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
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
