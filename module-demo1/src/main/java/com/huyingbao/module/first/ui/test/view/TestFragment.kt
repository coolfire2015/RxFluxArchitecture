package com.huyingbao.module.first.ui.test.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.first.R
import com.huyingbao.module.first.ui.test.store.TestStore

class TestFragment : BaseFluxFragment<TestStore>() {
    companion object {
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_test
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
