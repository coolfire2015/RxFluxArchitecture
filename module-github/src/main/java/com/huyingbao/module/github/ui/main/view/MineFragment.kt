package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.store.MainStore

class MineFragment : BaseRxFragment<MainStore>() {
    companion object {
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_mine
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}