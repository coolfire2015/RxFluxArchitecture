package com.huyingbao.module.github.ui.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.search.store.SearchStore

class SearchActivity : BaseRxFragActivity<SearchStore>() {
    override fun createFragment(): Fragment? {
        return SearchFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
