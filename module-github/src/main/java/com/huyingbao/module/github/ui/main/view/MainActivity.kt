package com.huyingbao.module.github.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.huyingbao.core.base.activity.BaseRxActivity
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.store.MainStore
import com.huyingbao.module.github.ui.user.view.UserActivity
import kotlinx.android.synthetic.main.github_activity_main.*
import javax.inject.Inject

class MainActivity : BaseRxActivity<MainStore>(), NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int {
        return R.layout.github_activity_main
    }

    @Inject
    lateinit var mainActionCreator: MainActionCreator

    override fun afterCreate(savedInstanceState: Bundle?) {
        mainActionCreator.getLoginUserInfo()
        initDrawerLayout()
        initNavigationView()

    }

    /**
     * 初始化左侧抽屉布局
     */
    private fun initDrawerLayout() {
        //设置ToolBar
        setSupportActionBar(toolbar_main)
        //设置DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout_main, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout_main?.addDrawerListener(toggle)
        toggle.syncState()

    }

    /**
     * 初始化左侧隐藏菜单布局
     */
    private fun initNavigationView() {
        nav_view_main?.setNavigationItemSelectedListener(this)
        rxStore?.mUser?.observe(this, Observer {
            if (it != null) {
                nav_view_main.findViewById<TextView>(R.id.tv_user_name).text = it.name
                nav_view_main.findViewById<TextView>(R.id.tv_user_email).text = it.email
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


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_main_feedback -> {
                startActivity(Intent(this,UserActivity::class.java))
            }
            R.id.nav_main_user_info->{

            }
            R.id.nav_main_about->{

            }
            R.id.nav_main_version->{

            }
            R.id.nav_main_logout->{

            }

        }
        drawer_layout_main?.closeDrawer(GravityCompat.START)
        return true
    }
}