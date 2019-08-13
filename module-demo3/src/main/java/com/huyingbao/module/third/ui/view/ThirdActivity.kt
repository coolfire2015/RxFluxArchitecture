package com.huyingbao.module.third.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.third.ui.store.ThirdStore

class ThirdActivity : BaseFluxFragActivity<ThirdStore>() {
    override fun createFragment(): Fragment? {
        return ThirdFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
