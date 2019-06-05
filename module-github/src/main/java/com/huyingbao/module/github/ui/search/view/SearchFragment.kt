package com.huyingbao.module.github.ui.search.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.search.store.SearchStore

class  SearchFragment : BaseRxFragment<SearchStore>() {
    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_search
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
