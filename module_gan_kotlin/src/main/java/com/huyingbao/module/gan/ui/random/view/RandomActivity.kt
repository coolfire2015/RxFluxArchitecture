package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle

import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.common.view.CommonRxActivity
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.store.RandomStore

import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.Lazy

/**
 * Created by liujunfeng on 2019/1/1.
 */
class RandomActivity : CommonRxActivity<RandomStore>() {
    @Inject
    internal var mCategoryFragmentLazy: Lazy<CategoryFragment>? = null
    @Inject
    internal var mProductListFragmentLazy: Lazy<ProductFragment>? = null

    override fun createFragment(): Fragment {
        return mCategoryFragmentLazy!!.get()
    }

    override fun afterCreate(savedInstanceState: Bundle) {}

    @Subscribe(sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        super.onRxChanged(rxChange)
        when (rxChange.tag) {
            RandomAction.TO_SHOW_DATA -> addFragmentHideExisting(mProductListFragmentLazy!!.get())
        }
    }
}
