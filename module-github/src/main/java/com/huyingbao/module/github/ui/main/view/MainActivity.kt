package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.main.store.MainStore

class MainActivity : BaseRxFragActivity<MainStore>() {
    override fun createFragment(): Fragment? {
        return MainFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}