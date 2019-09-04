package com.huyingbao.module.first.ui.tmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.first.ui.tmp.store.TmpStore

class TmpActivity : BaseFluxFragActivity<TmpStore>() {
    override fun createFragment(): Fragment? {
        return TmpFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
