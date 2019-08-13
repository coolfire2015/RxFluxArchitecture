package com.huyingbao.module.second.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.second.ui.store.SecondStore

class SecondActivity : BaseFluxFragActivity<SecondStore>() {
    override fun createFragment(): Fragment? {
        return SecondFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
