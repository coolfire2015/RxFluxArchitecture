package com.huyingbao.module.first.ui.test.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.first.ui.test.store.TestStore

class TestActivity : BaseFluxFragActivity<TestStore>() {
    override fun createFragment(): Fragment? {
        return TestFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
