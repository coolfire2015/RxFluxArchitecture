package com.huyingbao.module.third.ui.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.third.R
import com.huyingbao.module.third.ui.store.ThirdStore

class ThirdFragment : BaseFluxFragment<ThirdStore>() {
    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.third_fragment_third
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_third, true)
    }
}
