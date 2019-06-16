package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.store.MainStore
import javax.inject.Inject

/**
 * 动态页面
 *
 * Created by liujunfeng on 2019/6/10.
 */
class DynamicFragment : BaseRxFragment<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator
    @Inject
    lateinit var githubAppStore: GithubAppStore
    companion object {
        fun newInstance(): DynamicFragment {
            return DynamicFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_dynamic
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        githubAppStore.mUser.value?.login?.let { mainActionCreator.getNewsEvent(it,1) }
    }
}