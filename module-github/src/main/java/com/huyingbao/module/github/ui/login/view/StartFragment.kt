package com.huyingbao.module.github.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.Lifecycle
import com.huyingbao.core.arch.RxFlux.TAG
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.utils.NavigationUtils
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * 引导页面
 *
 * Created by liujunfeng on 2019/5/31.
 */
class StartFragment : BaseRxFragment<LoginStore>() {
    override fun getLayoutId(): Int {
        return R.layout.github_fragment_start
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        Observable
                .timer(2, TimeUnit.SECONDS)
                .doOnDispose { Log.i(TAG, "Disposing subscription ON_DESTROY") }
                .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                .subscribe {
                    if (TextUtils.isEmpty(rxStore?.getAccessToken())) {
                        NavigationUtils.navigate(view!!, R.id.start_to_login, popUp = true)
                    } else {
                        NavigationUtils.navigate(view!!, R.id.start_to_main, args = null, popUp = true, finishStack = true)
                    }
                }
    }
}