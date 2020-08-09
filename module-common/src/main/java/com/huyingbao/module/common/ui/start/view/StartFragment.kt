package com.huyingbao.module.common.ui.start.view

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.common.fragment.BaseCommonFragment
import com.huyingbao.core.uitls.AndroidUtils
import com.huyingbao.module.common.R
import com.huyingbao.module.common.app.CommonAppConstants

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
        val appRouter = CommonAppConstants.Router.getAppRouter(AndroidUtils.getApplicationLabel(context))
        ARouter.getInstance().build(appRouter).navigation()
        activity?.finish()
    }
}
