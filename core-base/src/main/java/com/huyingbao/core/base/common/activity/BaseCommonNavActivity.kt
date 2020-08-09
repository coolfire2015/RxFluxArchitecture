package com.huyingbao.core.base.common.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NavigationRes
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.huyingbao.core.base.R
import com.huyingbao.core.utils.setNavigation


/**
 * 不使用Dagger.Android，不持有ViewModel，不自动管理订阅
 *
 * 使用[Navigation]管理Fragment
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseCommonNavActivity :
        BaseCommonActivity() {
    /**
     * 使用默认Activity布局，可以覆盖该方法，使用自定义布局
     */
    override fun getLayoutId(): Int {
        return R.layout.base_activity_nav
    }

    /**
     * 自定义[Navigation]资源文件
     */
    @NavigationRes
    protected abstract fun getGraphId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigation(getFragmentContainerId(), getGraphId())
    }

    override fun onSupportNavigateUp() = findNavController(getFragmentContainerId()).navigateUp()

    /**
     * [androidx.appcompat.widget.Toolbar]Menu点击事件，拦截返回按钮，[androidx.navigation.NavController.navigateUp]弹出 Fragment
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                if (findNavController(getFragmentContainerId()).navigateUp()) {
                    return true
                }
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}