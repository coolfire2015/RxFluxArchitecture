package com.huyingbao.module.github.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
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
import com.huyingbao.core.image.ImageLoader
import com.huyingbao.core.image.ImageLoaderUtils
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
        setTitle(R.string.app_github_name)
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
        //使用父类是ActionBarDrawerToggle的匿名内部类，复写抽屉关闭方法
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
                this, drawer_layout_main, findViewById(R.id.tlb_top), R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                //当抽屉关闭时，设置NavigationView中的所有MenuItem为未选中状态
                nav_view_main?.menu?.forEach {
                    it.isChecked = false
                    if (it.hasSubMenu()) {
                        it.subMenu.forEach { it2 ->
                            it2.isChecked = false
                        }
                    }
                }
            }
        }
        drawer_layout_main?.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    /**
     * 初始化左侧导航View，
     * 设置Menu点击事件，
     * 设置View信息显示。
     */
    private fun initNavigationView() {
        //修改日夜模式Menu图标文字
        nav_view_main?.menu?.findItem(R.id.nav_main_night)?.let {
            when (localStorageUtils.getValue(CommonContants.Key.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)) {
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    it.icon = getDrawable(R.drawable.ic_day)
                    it.title = getText(R.string.github_menu_day)
                }
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    it.icon = getDrawable(R.drawable.ic_night)
                    it.title = getText(R.string.github_menu_night)
                }
                else -> {
                    it.icon = getDrawable(R.drawable.ic_night)
                    it.title = getText(R.string.github_menu_night)
                }
            }
        }
        //Menu点击事件，在Group中设置checkableBehavior="single"，设置MenuItem选中效果
        nav_view_main?.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_main_feedback -> showFeedBackDialog()
                R.id.nav_main_user_info -> startActivity(Intent(this, UserActivity::class.java))
                R.id.nav_main_about -> showAbout()
                R.id.nav_main_version -> checkUpdate()
                R.id.nav_main_night -> changeNight()
                R.id.nav_main_wan -> ARouter.getInstance().build(CommonContants.Address.ArticleActivity).navigation()
                R.id.nav_main_gan -> ARouter.getInstance().build(CommonContants.Address.RandomActivity).navigation()
                R.id.nav_main_logout -> logout()
            }
            //关闭抽屉 drawer_layout_main?.closeDrawer(GravityCompat.START)
            //点击设置选中效果
            it.isChecked = true
            true
        }
        //NavigationView头部布局View当前登录的用户信息更新
        githubAppStore.userLiveData.observe(this, Observer {
            if (it != null) {
                //当数据变化更新UI
                val headerView = nav_view_main.getHeaderView(0)
                if (headerView is LinearLayout) {
                    //头像
                    val imageLoader = ImageLoader.Builder<String>()
                    imageLoader.isCircle = true
                    imageLoader.resource = it.avatarUrl
                    imageLoader.errorHolder = R.drawable.ic_main
                    imageLoader.imgView = headerView.getChildAt(0) as ImageView
                    ImageLoaderUtils.loadImage(this, imageLoader.build())
                    //用户名
                    (headerView.getChildAt(1) as TextView).text = it.login
                    //邮箱
                    (headerView.getChildAt(2) as TextView).text = it.email
                }
            }
        })
    }

    /**
     * 初始化底部导航View，
     * 设置Menu点击事件。
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
     * 检查更新
     */
    private fun checkUpdate() {

    }

    /**
     * 切换夜间模式
     */
    private fun changeNight() {
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