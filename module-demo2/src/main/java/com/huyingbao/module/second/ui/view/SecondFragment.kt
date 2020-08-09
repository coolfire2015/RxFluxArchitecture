package com.huyingbao.module.second.ui.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.second.R
import com.huyingbao.module.second.ui.store.SecondStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : BaseFluxFragment<SecondStore>() {
    companion object {
        fun newInstance(): SecondFragment {
            return SecondFragment()
        }
    }

    override val rxStore: SecondStore? by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.second_fragment_second
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_second, true)
    }

    fun main() {

    }
}
