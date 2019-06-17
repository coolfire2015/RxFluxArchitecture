package com.huyingbao.module.github.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.R2
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.action.LoginActionCreator
import com.huyingbao.module.github.ui.login.store.LoginStore
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * 登录页面
 *
 * Created by liujunfeng on 2019/1/1.
 */
class LoginFragment : BaseRxFragment<LoginStore>() {
    @Inject
    lateinit var mActionCreator: LoginActionCreator

    @BindView(R2.id.et_username)
    lateinit var mEtUsername: EditText
    @BindView(R2.id.et_password)
    lateinit var mEtPassword: EditText

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
        mEtUsername.setText(rxStore?.getUserName())
        mEtPassword.setText(rxStore?.getPassword())
    }

    /**
     * 点击登录
     */
    @OnClick(R2.id.btn_login)
    fun login() {
        val username = mEtUsername.text.toString()
        val password = mEtPassword.text.toString()
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "请输入密码！", Toast.LENGTH_SHORT).show()
            return
        }
        mActionCreator.login(username, password)
    }

    /**
     * 登录成功，获取当前登录用户信息
     */
    @Subscribe(tags = [LoginAction.LOGIN], sticky = true)
    fun onLogin(rxAction: RxAction) {
        mActionCreator.getLoginUserInfo()
    }
}
