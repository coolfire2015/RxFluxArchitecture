package com.huyingbao.core.base.flux.activity

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.base.R


/**
 * 使用[Navigation]管理Fragment
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseFluxNavActivity<T : RxActivityStore> :
        BaseFluxActivity<T>() {
    override fun getLayoutId(): Int {
        return R.layout.base_activity_nav
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    /**
     * 设置导航组件[Navigation]
     */
    private fun initNavigation() {
        //添加显示的Fragment
        val fragmentManager = supportFragmentManager
        //从fragment队列中获取资源ID标识的fragment，如果不存在，则返回
        val navHostFragment = (fragmentManager.findFragmentById(R.id.fragment_container)
                ?: return) as? NavHostFragment
                ?: return
        //设置导航解析文件
        navHostFragment.navController.setGraph(getGraphId())
    }

    /**
     * [Navigation]资源文件
     */
    @NavigationRes
    protected abstract fun getGraphId(): Int
}