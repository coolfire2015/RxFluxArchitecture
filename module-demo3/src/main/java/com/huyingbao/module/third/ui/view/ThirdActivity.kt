package com.huyingbao.module.third.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.third.ui.store.ThirdStore
import dagger.hilt.android.AndroidEntryPoint

@Route(path = CommonAppConstants.Router.ThirdActivity)
@AndroidEntryPoint
class ThirdActivity : BaseFluxFragActivity<ThirdStore>() {
    override val rxStore: ThirdStore? by viewModels()

    override fun createFragment(): Fragment? {
        return ThirdFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
