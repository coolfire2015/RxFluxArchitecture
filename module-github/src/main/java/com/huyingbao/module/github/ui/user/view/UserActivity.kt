package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import com.huyingbao.core.base.activity.BaseRxNavActivity
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.store.MainStore

class UserActivity : BaseRxNavActivity<MainStore>() {
    override fun getGraphId(): Int {
        return R.navigation.github_nav_user
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}