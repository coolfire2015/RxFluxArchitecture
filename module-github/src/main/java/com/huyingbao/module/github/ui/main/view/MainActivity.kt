package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.store.MainStore
import javax.inject.Inject

class MainActivity : BaseRxFragActivity<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    override fun createFragment(): Fragment? {
        return MainFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        mainActionCreator.getLoginUserInfo()
    }
}