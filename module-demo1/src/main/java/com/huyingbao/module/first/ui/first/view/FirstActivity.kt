package com.huyingbao.module.first.ui.first.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.first.ui.first.store.FirstStore
import com.orhanobut.logger.Logger
import java.util.*
import javax.inject.Inject

@Route(path = CommonAppConstants.Router.FirstActivity)
class FirstActivity : BaseFluxFragActivity<FirstStore>() {
    @Inject
    lateinit var list: ArrayList<String>

    override fun createFragment(): Fragment? {
        return FirstFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        Logger.e("个数${list.size}")
    }
}
