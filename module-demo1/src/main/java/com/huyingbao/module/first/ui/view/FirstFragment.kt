package com.huyingbao.module.first.ui.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.first.R
import com.huyingbao.module.first.ui.action.FirstActionCreator
import com.huyingbao.module.first.ui.store.FirstStore
import kotlinx.android.synthetic.main.first_fragment_first.*
import javax.inject.Inject

class FirstFragment : BaseFluxFragment<FirstStore>() {
    @Inject
    lateinit var firstActionCreator: FirstActionCreator

    companion object {
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.first_fragment_first
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        rxStore?.hotKeyLiveData?.observe(this, Observer { tv_hot_key.text = it.toString() })
        bt_get_hot_key.setOnClickListener { firstActionCreator.getHotKey() }
    }
}
