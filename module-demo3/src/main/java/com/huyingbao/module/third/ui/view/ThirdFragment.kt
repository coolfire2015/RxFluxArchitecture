package com.huyingbao.module.third.ui.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.third.R
import com.huyingbao.module.third.ui.store.ThirdStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdFragment : BaseFluxFragment<ThirdStore>() {
    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }

    override val rxStore: ThirdStore? by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.third_fragment_third
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_third, true)
    }
}
