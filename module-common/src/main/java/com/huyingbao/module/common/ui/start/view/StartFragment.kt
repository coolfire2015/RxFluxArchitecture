package com.huyingbao.module.common.ui.start.view

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.common.R
import com.huyingbao.module.common.ui.start.action.StartActionCreator
import com.huyingbao.module.common.ui.start.store.StartStore
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
class StartFragment : BaseRxFragment<StartStore>() {
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
        //延迟2000mm，跳转
        Observable
                .timer(2000, TimeUnit.MILLISECONDS)
                .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                .subscribe {
                }
    }
}
