package com.huyingbao.module.github.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.action.LoginActionCreator
import com.huyingbao.module.github.ui.login.store.LoginStore
import kotlinx.android.synthetic.main.github_fragment_login.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * 登录页面
 *
 * Created by liujunfeng on 2019/1/1.
 */
class LoginFragment : BaseRxFragment<LoginStore>() {
    @Inject
    lateinit var loginActionCreator: LoginActionCreator

    companion object{
        fun newInstance():LoginFragment{
            return LoginFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_login
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.github_label_login, false)
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        et_username.setText(rxStore?.getUserName())
        et_password.setText(rxStore?.getPassword())
        btn_login.setOnClickListener { clickLogin() }
    }

    /**
     * 点击登录
     */
    private fun clickLogin() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            context?.toast("请输入内容！")
        } else {
            loginActionCreator.login(username, password)
        }
    }

    /**
     * 登录成功，获取当前登录用户信息
     */
    @Subscribe(tags = [LoginAction.LOGIN], sticky = true)
    fun onLogin(rxAction: RxAction) {
        loginActionCreator.getLoginUserInfo()
    }
}
