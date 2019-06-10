package com.huyingbao.module.github.ui.repos.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.repos.store.ReposStore

class ReposActivity : BaseRxFragActivity<ReposStore>() {
    override fun createFragment(): Fragment? {
        return ReposFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
