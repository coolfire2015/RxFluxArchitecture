package com.huyingbao.module.common.ui.start

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.common.fragment.BaseCommonFragment
import com.huyingbao.module.common.R
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.AndroidUtils


/**
 * 引导页面
 *
 * Created by liujunfeng on 2019/5/31.
 */
class StartFragment : BaseFragment() {
    companion object {
        fun newInstance() = StartFragment()
    }

    override fun getLayoutId() = R.layout.common_fragment_start

    override fun afterCreate(savedInstanceState: Bundle?) {
        val appRouter =
            CommonAppConstants.Router.getAppRouter(AndroidUtils.getApplicationLabel(context))
        ARouter.getInstance().build(appRouter).navigation()
        activity?.finish()
    }
}
