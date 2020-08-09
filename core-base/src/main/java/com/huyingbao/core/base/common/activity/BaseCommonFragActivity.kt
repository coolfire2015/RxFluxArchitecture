package com.huyingbao.core.base.common.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.huyingbao.core.base.R
import com.huyingbao.core.utils.FragmentOp
import com.huyingbao.core.utils.setFragment

/**
 * 不使用Dagger.Android，不持有ViewModel，不自动管理订阅
 *
 * 使用[FragmentManager]管理Fragment
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseCommonFragActivity :
        BaseCommonActivity() {
    /**
     * 使用默认Activity布局，可以覆盖该方法，使用自定义布局
     */
    override fun getLayoutId(): Int {
        return R.layout.base_activity_frag
    }

    /**
     * 提供activity需要显示的fragment
     */
    protected abstract fun createFragment(): Fragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(getFragmentContainerId(), createFragment(), FragmentOp.OP_NULL)
    }
}