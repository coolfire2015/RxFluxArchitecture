package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.huyingbao.core.base.fragment.BaseRxBindFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.user.store.UserStore
import javax.inject.Inject


class UserFragment : BaseRxBindFragment<UserStore>() {

    @Inject
    lateinit var githubAppStore: GithubAppStore

    companion object {
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun getDataBindingView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
        val fragmentBlankBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.github_fragment_user, container, false)
        return fragmentBlankBinding.root
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.github_label_user, true)
    }
}