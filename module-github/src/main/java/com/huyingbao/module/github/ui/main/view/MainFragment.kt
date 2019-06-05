package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.store.MainStore

class MainFragment : BaseRxFragment<MainStore>() {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_main
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}