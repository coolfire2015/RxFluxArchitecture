package com.huyingbao.module.first.ui.first.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.first.ui.first.store.FirstStore
import dagger.hilt.android.AndroidEntryPoint

@Route(path = CommonAppConstants.Router.FirstActivity)
@AndroidEntryPoint
class FirstActivity : BaseFluxFragActivity<FirstStore>() {
    override val rxStore: FirstStore? by viewModels()

    override fun createFragment(): Fragment? {
        return FirstFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        rxStore?.hotKeyLiveData?.observe(this, Observer { })
    }
}
