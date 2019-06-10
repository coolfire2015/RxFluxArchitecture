package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.store.MainStore

class UserFragment : BaseRxFragment<MainStore>() {
    override fun getLayoutId(): Int {
        return R.layout.github_fragment_user
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.github_label_user, true)
    }
}