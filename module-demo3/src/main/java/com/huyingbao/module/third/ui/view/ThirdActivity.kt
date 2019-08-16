package com.huyingbao.module.third.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonRouter
import com.huyingbao.module.third.ui.store.ThirdStore

@Route(path = CommonRouter.ThirdActivity)
class ThirdActivity : BaseFluxFragActivity<ThirdStore>() {
    override fun createFragment(): Fragment? {
        return ThirdFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
