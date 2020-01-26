package com.huyingbao.module.common.ui.start.view

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.common.fragment.BaseCommonFragment
import com.huyingbao.core.utils.AndroidUtils
import com.huyingbao.module.common.R
import com.huyingbao.module.common.app.CommonAppConstants
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
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
        //延迟1500mm，跳转
        Observable
                .timer(1500, TimeUnit.MILLISECONDS)
                .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                .subscribe {
                    val appRouter = CommonAppConstants.Router.getAppRouter(AndroidUtils.getApplicationLabel(context))
                    ARouter.getInstance().build(appRouter).navigation()
                    activity?.finish()
                }
    }
}
