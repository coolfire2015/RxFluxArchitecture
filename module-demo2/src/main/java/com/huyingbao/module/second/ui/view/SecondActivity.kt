package com.huyingbao.module.second.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.second.ui.store.SecondStore
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@Route(path = CommonAppConstants.Router.SecondActivity)
@AndroidEntryPoint
class SecondActivity : BaseFluxFragActivity<SecondStore>() {
    override val rxStore: SecondStore? by viewModels()

    override fun createFragment(): Fragment? {
        return SecondFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        Logger.e("rxStore$rxStore");
    }
}
