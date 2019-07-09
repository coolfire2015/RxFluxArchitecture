package com.huyingbao.module.github.ui.start.view

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Lifecycle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.start.action.StartAction
import com.huyingbao.module.github.ui.start.action.StartActionCreator
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
    lateinit var githubAppStore: GithubAppStore
    @Inject
    lateinit var startActionCreator: StartActionCreator

    companion object {
        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_start
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        //获取当前登录的员工洗洗
        startActionCreator.getLoginUserInfo()
        //延迟2000mm，跳转
        Observable
                .timer(2000, TimeUnit.MILLISECONDS)
                .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                .subscribe {
                    if (TextUtils.isEmpty(githubAppStore.getAccessToken())) {
                        //无token，跳转登录页面（发送RxAction到StartStore，StoreStore再发送RxChange到LoginActivity）
                        startActionCreator.postLocalAction(StartAction.TO_LOGIN)
                    } else {
                        //有token，跳转主页面（直接发送RxChange到LoginActivity）
                        startActionCreator.postLocalChange(StartAction.TO_MAIN)
                    }
                }
    }
}
