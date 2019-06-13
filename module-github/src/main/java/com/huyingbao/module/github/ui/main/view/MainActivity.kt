package com.huyingbao.module.github.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.huyingbao.core.base.activity.BaseRxActivity
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.store.MainStore
import com.huyingbao.module.github.ui.user.view.UserActivity
import kotlinx.android.synthetic.main.github_activity_main.*
import javax.inject.Inject

/**
 * 主页，包含左侧抽屉
 *
 * Created by liujunfeng on 2019/6/10.
 */
class MainActivity : BaseRxActivity<MainStore>() {
    @Inject
    lateinit var githubAppStore: GithubAppStore

    override fun getLayoutId(): Int {
        return R.layout.github_activity_main
    }

    @Inject
    lateinit var mainActionCreator: MainActionCreator

    override fun afterCreate(savedInstanceState: Bundle?) {
        mainActionCreator.getLoginUserInfo()
        initDrawerLayout()
        initNavigationView()
        initBottomNavigationView()
        initViewPager()
    }

    /**
     * 初始化左侧抽屉控件
     */
    private fun initDrawerLayout() {
        //设置DrawerLayout
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout_main,
                findViewById(R.id.tlb_top), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout_main?.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    /**
     * 初始化左侧导航View，
     * 设置菜单点击事件，
     * 设置View信息显示。
     */
    private fun initNavigationView() {
        nav_view_main?.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_main_feedback -> {
                    startActivity(Intent(this, UserActivity::class.java))
                }
                R.id.nav_main_user_info -> {

                }
                R.id.nav_main_about -> {

                }
                R.id.nav_main_version -> {

                }
                R.id.nav_main_logout -> {

                }
            }
            drawer_layout_main?.closeDrawer(GravityCompat.START)
            true
        }
        githubAppStore.mUser.observe(this, Observer {
            if (it != null && this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                //当数据变化，不为空，且当前界面正在显示时，更新UI
                nav_view_main?.findViewById<TextView>(R.id.tv_user_name)?.text = it.name
                nav_view_main?.findViewById<TextView>(R.id.tv_user_email)?.text = it.email
            }
        })
    }


    /**
     * 初始化底部导航View，
     * 设置菜单点击事件。
     */
    private fun initBottomNavigationView() {
        bottom_nav_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_dynamic -> {
                    view_pager_main.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_recommend -> {
                    view_pager_main.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_mine -> {
                    view_pager_main.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    /**
     * 初始化ViewPage2
     */
    private fun initViewPager() {
        //设置适配器，生成对应的Fragment
        view_pager_main.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> DynamicFragment()
                    1 -> RecommendFragment()
                    2 -> MineFragment()
                    else -> DynamicFragment()
                }
            }

            override fun getItemCount(): Int {
                return 3
            }
        }
        //设置滑动回调
        view_pager_main.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                return when (position) {
                    0 -> bottom_nav_main.selectedItemId = R.id.bottom_nav_dynamic
                    1 -> bottom_nav_main.selectedItemId = R.id.bottom_nav_recommend
                    2 -> bottom_nav_main.selectedItemId = R.id.bottom_nav_mine
                    else -> bottom_nav_main.selectedItemId = R.id.bottom_nav_dynamic
                }
            }
        })
    }


    override fun onBackPressed() {
        if (drawer_layout_main.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_main?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}