package com.huyingbao.module.github.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.activity.BaseRxActivity
import com.huyingbao.core.common.dialog.CommonInfo
import com.huyingbao.core.common.dialog.CommonInfoDialog
import com.huyingbao.core.common.dialog.CommonInfoDialogClickListener
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.store.MainStore
import com.huyingbao.module.github.ui.user.view.UserActivity
import kotlinx.android.synthetic.main.github_activity_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import javax.inject.Inject

/**
 * 主页，包含左侧抽屉
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Route(path = CommonContants.Address.MainActivity)
class MainActivity : BaseRxActivity<MainStore>() {
    @Inject
    lateinit var githubAppStore: GithubAppStore
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    override fun getLayoutId(): Int {
        return R.layout.github_activity_main
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        initDrawerLayout()
        initNavigationView()
        initBottomNavigationView()
        initViewPager()
    }

    /**
     * 拦截回退按钮，先关闭抽屉
     */
    override fun onBackPressed() {
        if (drawer_layout_main.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_main?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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
                R.id.nav_main_feedback -> showFeedBackDialog()
                R.id.nav_main_user_info -> startActivity(Intent(this, UserActivity::class.java))
                R.id.nav_main_about -> showAbout()
                R.id.nav_main_version -> checkUpdate()
                R.id.nav_main_wan -> ARouter.getInstance().build(CommonContants.Address.ArticleActivity).navigation()
                R.id.nav_main_gan -> ARouter.getInstance().build(CommonContants.Address.RandomActivity).navigation()
                R.id.nav_main_logout -> logout()
            }
            drawer_layout_main?.closeDrawer(GravityCompat.START)
            true
        }
        githubAppStore.userLiveData.observe(this, Observer {
            if (it != null) {
                //当数据变化更新UI
                val headerView = nav_view_main.getHeaderView(0)
                if (headerView is LinearLayout) {
                    (headerView.getChildAt(1) as TextView).text = it.login
                    (headerView.getChildAt(2) as TextView).text = it.email
                }
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
     * 初始化ViewPage
     */
    private fun initViewPager() {
        view_pager_main.offscreenPageLimit = 3
        //设置适配器，生成对应的Fragment
        view_pager_main.adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> DynamicFragment()
                    1 -> TrendFragment()
                    2 -> MineFragment()
                    else -> DynamicFragment()
                }
            }

            override fun getCount(): Int {
                return 3
            }
        }
        //设置滑动回调
        view_pager_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottom_nav_main.selectedItemId = R.id.bottom_nav_dynamic
                    1 -> bottom_nav_main.selectedItemId = R.id.bottom_nav_recommend
                    2 -> bottom_nav_main.selectedItemId = R.id.bottom_nav_mine
                    else -> bottom_nav_main.selectedItemId = R.id.bottom_nav_dynamic
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    /**
     * 显示更新内容弹框
     */
    private fun showFeedBackDialog() {
        val info = CommonInfo()
        info.title = getString(R.string.github_menu_feedback)
        info.editContent = ""
        val commonInfoDialog = CommonInfoDialog.newInstance(info)
        commonInfoDialog.clickListener = object : CommonInfoDialogClickListener {
            override fun onConfirm(editTitle: String, editContent: String) {
                mainActionCreator.feedback(editContent)
            }
        }
        commonInfoDialog.show(supportFragmentManager, CommonInfoDialog::class.java.simpleName)
    }

    /**
     * 切换夜间模式
     */
    private fun checkUpdate() {
        when (localStorageUtils.getValue(CommonContants.Key.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)) {
            AppCompatDelegate.MODE_NIGHT_NO -> localStorageUtils.setValue(CommonContants.Key.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.MODE_NIGHT_YES -> localStorageUtils.setValue(CommonContants.Key.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
            else -> localStorageUtils.setValue(CommonContants.Key.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
        }
        AppCompatDelegate.setDefaultNightMode(localStorageUtils.getValue(CommonContants.Key.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO))
        recreate()
    }

    /**
     * 显示关于
     */
    private fun showAbout() {

    }

    /**
     * 清除旧Token，跳转登录页面，结束当前页面
     */
    private fun logout() {
        localStorageUtils.setValue(CommonContants.Key.ACCESS_TOKEN, "")
        val intent = Intent(this, LoginActivity::class.java)
        intent.clearTask()
        intent.clearTop()
        startActivity(intent)
        finish()
    }
}