package com.huyingbao.module.github.ui.repos.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.repos.store.ReposStore

class ReposFragment : BaseRxFragment<ReposStore>() {
    companion object {
        fun newInstance(): ReposFragment {
            return ReposFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_repos
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
