package com.huyingbao.module.first.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonRouter
import com.huyingbao.module.first.ui.store.FirstStore

@Route(path = CommonRouter.FirstActivity)
class FirstActivity : BaseFluxFragActivity<FirstStore>() {
    override fun createFragment(): Fragment? {
        return FirstFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
