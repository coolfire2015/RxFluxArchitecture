package com.huyingbao.module.first.ui.first.view

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.first.R
import com.huyingbao.module.first.ui.first.action.FirstActionCreator
import com.huyingbao.module.first.ui.first.store.FirstStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.first_fragment_first.*
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : BaseFluxFragment<FirstStore>() {
    @Inject
    lateinit var firstActionCreator: FirstActionCreator

    companion object {
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }

    override val rxStore: FirstStore? by activityViewModels()

    override fun getLayoutId(): Int {
        return R.layout.first_fragment_first
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_first, false)
        rxStore?.hotKeyLiveData?.observe(this, Observer { tv_hot_key.text = it.toString() })
        bt_get_hot_key.setOnClickListener {
            firstActionCreator.getHotKey()
        }
        bt_to_demo2.setOnClickListener {
            ARouter.getInstance().build(CommonAppConstants.Router.SecondActivity).navigation()
        }
    }
}
