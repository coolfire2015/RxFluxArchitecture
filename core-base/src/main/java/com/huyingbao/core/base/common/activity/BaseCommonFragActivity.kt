package com.huyingbao.core.base.common.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.huyingbao.core.base.R
import com.huyingbao.core.utils.ActivityUtils


/**
 * 使用[FragmentManager]管理Fragment
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseCommonFragActivity :
        BaseCommonActivity() {
    override fun getLayoutId(): Int {
        return R.layout.base_activity_frag
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
    }

    /**
     * 设置需要展示的Fragment
     */
    private fun initFragment() {
        //如果不存在容纳Fragment的FrameLayout，则返回
        findViewById<FrameLayout>(R.id.fl_container) ?: return
        //从fragment队列中获取资源ID标识的fragment
        val fragmentOld = supportFragmentManager.findFragmentById(R.id.fl_container)
        //如果已经存在fragment或者创建fragment方法为空，则返回
        if (fragmentOld != null) {
            return
        }
        //使用新的Fragment，如果为空则返回
        val fragmentNew = createFragment() ?: return
        //使用fragment类名作为tag
        val tag = fragmentNew.javaClass.simpleName
        supportFragmentManager.beginTransaction().add(R.id.fl_container, fragmentNew, tag).commit()
    }

    /**
     * 提供activity需要显示的fragment
     */
    protected abstract fun createFragment(): Fragment?

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，隐藏旧的Fragment，添加新的Fragment
     */
    protected fun addFragmentHideExisting(fragment: Fragment) {
        ActivityUtils.setFragment(this, R.id.fl_container, fragment, true)
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，用新的Fragment替换旧的Fragment
     */
    protected fun addFragmentReplaceExisting(fragment: Fragment) {
        ActivityUtils.setFragment(this, R.id.fl_container, fragment, false)
    }
}