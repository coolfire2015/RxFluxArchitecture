package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.huyingbao.core.base.fragment.BaseRxBindFragment
import com.huyingbao.core.common.dialog.CommonInfo
import com.huyingbao.core.common.dialog.CommonInfoDialog
import com.huyingbao.core.common.dialog.CommonInfoDialogClickListener
import com.huyingbao.core.common.widget.CommonInfoCardView
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.databinding.GithubFragmentUserBinding
import com.huyingbao.module.github.ui.user.action.UserActionCreator
import com.huyingbao.module.github.ui.user.model.UserInfoRequest
import com.huyingbao.module.github.ui.user.store.UserStore
import kotlinx.android.synthetic.main.github_fragment_user.*
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
        cv_info_bio.setOnClickListener { showUpdateDialog(it, onUpdateContent(it)) }
        cv_info_blog.setOnClickListener { showUpdateDialog(it, onUpdateContent(it)) }
        cv_info_company.setOnClickListener { showUpdateDialog(it, onUpdateContent(it)) }
        cv_info_email.setOnClickListener { showUpdateDialog(it, onUpdateContent(it)) }
        cv_info_name.setOnClickListener { showUpdateDialog(it, onUpdateContent(it)) }
        cv_info_location.setOnClickListener { showUpdateDialog(it, onUpdateContent(it)) }
    }

    /**
     * 显示更新内容弹框
     */
    private fun showUpdateDialog(infoView: View, clickListener: CommonInfoDialogClickListener?) {
        if (infoView is CommonInfoCardView) {
            activity?.supportFragmentManager?.let {
                val info = CommonInfo()
                info.title = "编辑${infoView.infoTitle}"
                info.editContent = (infoView.infoContent ?: "") as String?
                val commonInfoDialog = CommonInfoDialog.newInstance(info)
                commonInfoDialog.clickListener = clickListener
                commonInfoDialog.show(it, CommonInfoDialog::class.java.simpleName)
            }
        }
    }

    /**
     * 接收点击框回调，编辑框内更新的内容，并修改用户信息
     */
    private fun onUpdateContent(view: View): CommonInfoDialogClickListener? {
        return object : CommonInfoDialogClickListener {
            override fun onConfirm(editTitle: String, editContent: String) {
                val it = UserInfoRequest()
                when (view.id) {
                    R.id.cv_info_name -> {
                        it.name = editContent
                        cv_info_name.infoContent = it.name
                    }
                    R.id.cv_info_email -> {
                        it.email = editContent
                        cv_info_email.infoContent = it.email
                    }
                    R.id.cv_info_location -> {
                        it.location = editContent
                        cv_info_location.infoContent = it.location
                    }
                    R.id.cv_info_blog -> {
                        it.blog = editContent
                        cv_info_blog.infoContent = it.blog
                    }
                    R.id.cv_info_company -> {
                        it.company = editContent
                        cv_info_company.infoContent = it.company
                    }
                    R.id.cv_info_bio -> {
                        it.bio = editContent
                        cv_info_bio.infoContent = it.bio
                    }
                }
                userActionCreator.updateUserInfo(it)
            }
        }
    }
}