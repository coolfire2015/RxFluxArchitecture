package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import androidx.lifecycle.Observer
import butterknife.OnClick
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.common.dialog.CommonInfoDialog
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.common.widget.CommonInfoCardView
import com.huyingbao.module.github.R
import com.huyingbao.module.github.R2
import com.huyingbao.module.github.app.BaseRxBindFragment
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.databinding.GithubFragmentUserBinding
import com.huyingbao.module.github.ui.user.action.UserAction
import com.huyingbao.module.github.ui.user.action.UserActionCreator
import com.huyingbao.module.github.ui.user.model.UserInfoRequest
import com.huyingbao.module.github.ui.user.store.UserStore
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * 当前登录用户信息展示修改页面
 *
 * Created by liujunfeng on 2019/6/10.
 */
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
    }

    /**
     * 点击事件
     */
    @OnClick(R2.id.cv_info_bio,
            R2.id.cv_info_blog,
            R2.id.cv_info_email,
            R2.id.cv_info_company,
            R2.id.cv_info_name,
            R2.id.cv_info_location)
    fun onClick(view: CommonInfoCardView) {
        when (view.id) {
            R.id.cv_info_bio -> showUpdateDialog(view, UserAction.UPDATE_USER_BIO)
            R.id.cv_info_blog -> showUpdateDialog(view, UserAction.UPDATE_USER_BLOG)
            R.id.cv_info_company -> showUpdateDialog(view, UserAction.UPDATE_USER_COMPANY)
            R.id.cv_info_email -> showUpdateDialog(view, UserAction.UPDATE_USER_EMAIL)
            R.id.cv_info_name -> showUpdateDialog(view, UserAction.UPDATE_USER_NAME)
            R.id.cv_info_location -> showUpdateDialog(view, UserAction.UPDATE_USER_LOCATION)
        }
    }

    /**
     * 显示更新内容弹框
     */
    private fun showUpdateDialog(view: CommonInfoCardView, tag: String) {
        commonInfoDialog.info = CommonInfoDialog.Info()
        commonInfoDialog.info.title = "编辑"
        commonInfoDialog.info.actionFirst = tag
        commonInfoDialog.info.editContent = view.infoContent
        activity?.supportFragmentManager?.let { fragmentManager -> commonInfoDialog.show(fragmentManager, commonInfoDialog.javaClass.simpleName) }
    }

    /**
     * 接收编辑框内更新的内容，并修改用户信息
     */
    @Subscribe(sticky = true, tags = [
        UserAction.UPDATE_USER_NAME,
        UserAction.UPDATE_USER_EMAIL,
        UserAction.UPDATE_USER_LOCATION,
        UserAction.UPDATE_USER_BLOG,
        UserAction.UPDATE_USER_COMPANY,
        UserAction.UPDATE_USER_BIO])
    fun onUpdateContent(rxAction: RxAction) {
        val userInfoRequest = UserInfoRequest()
        when (rxAction.tag) {
            UserAction.UPDATE_USER_NAME -> userInfoRequest.name = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_EMAIL -> userInfoRequest.email = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_LOCATION -> userInfoRequest.location = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_BLOG -> userInfoRequest.blog = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_COMPANY -> userInfoRequest.company = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_BIO -> userInfoRequest.bio = rxAction.data[CommonContants.Key.CONTENT].toString()
        }
        userActionCreator.updateUserInfo(userInfoRequest)
    }
}