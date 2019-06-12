package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.databinding.GithubFragmentUserBinding
import com.huyingbao.module.github.ui.user.store.UserStore
import com.huyingbao.module.github.utils.BaseRxBindFragment
import javax.inject.Inject


class UserFragment : BaseRxBindFragment<UserStore, GithubFragmentUserBinding>() {

    @Inject
    lateinit var githubAppStore: GithubAppStore

    companion object {
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_user
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.github_label_user, true)
        githubAppStore.mUser.observe(this, Observer {
            binding?.userInfo = it
        })
    }
}