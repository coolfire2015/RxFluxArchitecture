package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.common.dialog.CommonInfoDialog
import com.huyingbao.core.common.widget.CommonInfoCardView
import com.huyingbao.module.github.R
import com.huyingbao.core.base.fragment.BaseRxBindFragment
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.databinding.GithubFragmentUserBinding
import com.huyingbao.module.github.ui.user.action.UserAction
import com.huyingbao.module.github.ui.user.action.UserActionCreator
import com.huyingbao.module.github.ui.user.store.UserStore
import kotlinx.android.synthetic.main.github_fragment_user.*
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
        initView()
    }

    /**
     * 初始化
     */
    private fun initView() {
        githubAppStore.userLiveData.observe(this, Observer {
            binding?.userInfo = it
        })
        cv_info_bio.setOnClickListener { showUpdateDialog(it, UserAction.UPDATE_USER_BIO) }
        cv_info_blog.setOnClickListener { showUpdateDialog(it, UserAction.UPDATE_USER_BLOG) }
        cv_info_company.setOnClickListener { showUpdateDialog(it, UserAction.UPDATE_USER_COMPANY) }
        cv_info_email.setOnClickListener { showUpdateDialog(it, UserAction.UPDATE_USER_EMAIL) }
        cv_info_name.setOnClickListener { showUpdateDialog(it, UserAction.UPDATE_USER_NAME) }
        cv_info_location.setOnClickListener { showUpdateDialog(it, UserAction.UPDATE_USER_LOCATION) }
    }

    /**
     * 显示更新内容弹框
     */
    private fun showUpdateDialog(infoView: View, tag: String) {
        if (infoView is CommonInfoCardView) {
            commonInfoDialog.info = CommonInfoDialog.Info()
            commonInfoDialog.info!!.title = "编辑${infoView.infoTitle}"
            commonInfoDialog.info!!.actionFirst = tag
            commonInfoDialog.info!!.editContent = infoView.infoContent ?: ""
            activity?.supportFragmentManager?.let { fragmentManager -> commonInfoDialog.show(fragmentManager, commonInfoDialog.javaClass.simpleName) }
        }
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
    fun onUpdateContent(rxChange: RxChange) {
        rxStore?.mUserInfoRequest?.let {
            when (rxChange.tag) {
                UserAction.UPDATE_USER_NAME -> cv_info_name.infoContent = it.name
                UserAction.UPDATE_USER_EMAIL -> cv_info_email.infoContent = it.email
                UserAction.UPDATE_USER_LOCATION -> cv_info_location.infoContent = it.location
                UserAction.UPDATE_USER_BLOG -> cv_info_blog.infoContent = it.blog
                UserAction.UPDATE_USER_COMPANY -> cv_info_company.infoContent = it.company
                UserAction.UPDATE_USER_BIO -> cv_info_bio.infoContent = it.bio
            }
            userActionCreator.updateUserInfo(it)
        }
    }
}