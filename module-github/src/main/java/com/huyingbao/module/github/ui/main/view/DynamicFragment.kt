package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.store.MainStore

class DynamicFragment : BaseRxFragment<MainStore>() {
    companion object {
        fun newInstance(): DynamicFragment {
            return DynamicFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_dynamic
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}