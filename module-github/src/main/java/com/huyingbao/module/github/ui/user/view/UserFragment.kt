package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.common.dialog.CommonInfoDialog
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.BaseRxBindFragment
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.databinding.GithubFragmentUserBinding
import com.huyingbao.module.github.ui.user.action.UserAction
import com.huyingbao.module.github.ui.user.action.UserActionCreator
import com.huyingbao.module.github.ui.user.model.UserInfoRequest
import com.huyingbao.module.github.ui.user.store.UserStore
import kotlinx.android.synthetic.main.github_fragment_user.*
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class UserFragment : BaseRxBindFragment<UserStore, GithubFragmentUserBinding>() {
    @Inject
    lateinit var userActionCreator: UserActionCreator
    @Inject
    lateinit var githubAppStore: GithubAppStore
    @Inject
    lateinit var commonInfoDialog: CommonInfoDialog

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
        cv_info_name.setOnClickListener {
            commonInfoDialog.info = CommonInfoDialog.Info()
            commonInfoDialog.info.title = "编辑"
            commonInfoDialog.info.editContent = cv_info_name.infoContent
            commonInfoDialog.info.actionFirst = UserAction.UPDATE_CONTENT
            activity?.supportFragmentManager?.let { fragmentManager -> commonInfoDialog.show(fragmentManager, commonInfoDialog.javaClass.simpleName) }
        }
    }

    @Subscribe(tags = [UserAction.UPDATE_CONTENT], sticky = true)
    fun onUpdateContent(rxAction: RxAction) {
        val userInfoRequest = UserInfoRequest()
        userInfoRequest.name = rxAction.data[CommonContants.Key.CONTENT].toString()
        userActionCreator.updateUserInfo(userInfoRequest)
    }
}