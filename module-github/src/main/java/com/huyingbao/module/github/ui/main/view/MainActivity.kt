package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.huyingbao.core.base.activity.BaseRxActivity
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.store.MainStore
import kotlinx.android.synthetic.main.github_activity_main.*
import kotlinx.android.synthetic.main.github_nav_header_main.*
import javax.inject.Inject

class MainActivity : BaseRxActivity<MainStore>(), NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int {
        return R.layout.github_activity_main
    }

    @Inject
    lateinit var mainActionCreator: MainActionCreator

    override fun afterCreate(savedInstanceState: Bundle?) {
        mainActionCreator.getLoginUserInfo()
        initDrawer()
        rxStore?.mUser?.observe(this, Observer {
            if (it != null) {
                tv_user_email?.text = it.email
                tv_user_name?.text = it.name
            }
        })

    }

    /**
     * 初始化左侧抽屉布局
     */
    private fun initDrawer() {
        //设置ToolBar
        setSupportActionBar(toolbar)
        //设置DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout?.addDrawerListener(toggle)
        toggle.syncState()
        //设置NavigationView
        nav_view?.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }

        }
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }
}