package com.huyingbao.module.github.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.Lifecycle
import com.huyingbao.core.arch.RxFlux.TAG
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.core.common.utils.NavigationUtils
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.login.action.LoginActionCreator
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * 引导页面
 *
 * Created by liujunfeng on 2019/5/31.
 */
class StartFragment : BaseRxFragment<LoginStore>() {
    @Inject
    lateinit var loginActionCreator: LoginActionCreator

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_start
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(rxStore?.getAccessToken())) {
            //无token，跳转登录页面
            Observable
                    .timer(500, TimeUnit.MILLISECONDS)
                    .doOnDispose { Log.i(TAG, "Disposing subscription ON_DESTROY") }
                    .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                    .subscribe { NavigationUtils.navigate(view!!, R.id.start_to_login, popUp = true) }
        } else {
            //有token，获取当前登录用户信息
            loginActionCreator.getLoginUserInfo()
        }
    }
}