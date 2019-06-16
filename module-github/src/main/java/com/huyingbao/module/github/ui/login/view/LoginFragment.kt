package com.huyingbao.module.github.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.utils.LocalStorageUtils
import com.huyingbao.module.github.R
import com.huyingbao.module.github.R2
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.action.LoginActionCreator
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.utils.NavigationUtils
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
    @Inject
    lateinit var mLocalStorageUtils: LocalStorageUtils

    @BindView(R2.id.et_username)
    lateinit var mEtUsername: EditText
    @BindView(R2.id.et_password)
    lateinit var mEtPassword: EditText
    @BindView(R2.id.btn_login)
    lateinit var mBtnLogin: Button

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
        val userName: String? = mLocalStorageUtils.getValue(CommonContants.Key.USER_NAME, "")
        val password: String? = mLocalStorageUtils.getValue(CommonContants.Key.PASSWORD, "")
        mEtUsername.setText(userName)
        mEtPassword.setText(password)
    }

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

    @Subscribe(tags = [LoginAction.LOGIN], sticky = true)
    fun onLogin(rxAction: RxAction) {
        NavigationUtils.navigate(view!!, R.id.login_to_main, args = null, popUp = true, finishStack = true)
    }
}
