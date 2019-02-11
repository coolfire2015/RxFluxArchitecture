package com.huyingbao.module.gan.ui.main.view

import android.os.Bundle

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.view.CommonRxFragment
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.R2
import com.huyingbao.module.gan.ui.main.action.MainAction
import com.huyingbao.module.gan.ui.main.action.MainActionCreator
import com.huyingbao.module.gan.ui.main.store.MainStore

import javax.inject.Inject

import butterknife.OnClick

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class MainFragment @Inject
constructor() : CommonRxFragment<MainStore>() {
    @Inject
    internal var mActionCreator: MainActionCreator? = null


    override fun getLayoutId(): Int {
        return R.layout.gan_fragment_category
    }

    override fun afterCreate(savedInstanceState: Bundle) {
        setTitle(R.string.gan_label_main, false)
    }

    @OnClick(R2.id.btn_category_gan)
    fun toGanModule() {
        mActionCreator!!.postLocalAction(MainAction.TO_GAN_MODULE)
    }

    @OnClick(R2.id.btn_category_wan)
    fun toWanModule() {
        mActionCreator!!.postLocalAction(MainAction.TO_WAN_MODULE)
    }
}
