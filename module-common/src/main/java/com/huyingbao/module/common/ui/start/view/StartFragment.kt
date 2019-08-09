package com.huyingbao.module.common.ui.start.view

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.common.fragment.BaseCommonFragment
import com.huyingbao.core.common.utils.AndroidUtils
import com.huyingbao.module.common.R
import com.huyingbao.module.common.app.CommonRouter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * 引导页面
 *
 * Created by liujunfeng on 2019/5/31.
 */
class StartFragment : BaseCommonFragment() {

    companion object {
        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_start
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        //延迟2000mm，跳转
        Observable
                .timer(1500, TimeUnit.MILLISECONDS)
                .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                .subscribe {
                    val appRouter = CommonRouter.getAppRouter(AndroidUtils.getApplicationLabel(context))
                    ARouter.getInstance().build(appRouter).navigation()
                    activity?.finish()
                }
    }
}
