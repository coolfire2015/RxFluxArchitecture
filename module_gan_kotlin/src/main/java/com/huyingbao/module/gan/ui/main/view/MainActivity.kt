package com.huyingbao.module.gan.ui.main.view

import android.content.Intent
import android.os.Bundle

import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.common.view.CommonRxActivity
import com.huyingbao.module.gan.ui.main.action.MainAction
import com.huyingbao.module.gan.ui.main.store.MainStore
import com.huyingbao.module.gan.ui.random.view.RandomActivity

import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject
import androidx.fragment.app.Fragment
import dagger.Lazy

/**
 * Created by liujunfeng on 2019/1/1.
 */
class MainActivity : CommonRxActivity<MainStore>() {
    @Inject
    internal var mMainFragmentLazy: Lazy<MainFragment>? = null

    override fun createFragment(): Fragment {
        return mMainFragmentLazy!!.get()
    }

    override fun afterCreate(savedInstanceState: Bundle) {}

    /**
     * 接收RxChange，粘性
     */
    @Subscribe(sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        super.onRxChanged(rxChange)
        when (rxChange.tag) {
            MainAction.TO_GAN_MODULE -> startActivity(Intent(this, RandomActivity::class.java))
            MainAction.TO_WAN_MODULE -> ARouter.getInstance().build("/wan/ArticleActivity").navigation()
        }
    }
}
