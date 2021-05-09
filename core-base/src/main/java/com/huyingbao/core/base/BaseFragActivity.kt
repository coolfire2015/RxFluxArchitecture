package com.huyingbao.core.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * 不使用Dagger.Android，不持有ViewModel，不自动管理订阅
 *
 * 使用[FragmentManager]管理Fragment
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseFragActivity : BaseView,
        AppCompatActivity
        () {
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

    /**
     * [androidx.appcompat.widget.Toolbar]Menu点击事件，拦截返回按钮，如果Fragment回退栈不为空，先弹出Fragment
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    return true
                }
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}