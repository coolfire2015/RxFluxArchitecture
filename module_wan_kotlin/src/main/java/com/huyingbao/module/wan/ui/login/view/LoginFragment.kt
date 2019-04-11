package com.huyingbao.module.wan.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.base.rxview.CommonRxFragment
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.R2
import com.huyingbao.module.wan.ui.login.action.LoginActionCreator
import com.huyingbao.module.wan.ui.login.store.LoginStore
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class LoginFragment @Inject
constructor() : CommonRxFragment<LoginStore>() {
    @Inject
    lateinit var mActionCreator: LoginActionCreator

    @BindView(R2.id.et_username)
    lateinit var mEtUsername: EditText
    @BindView(R2.id.et_password)
    lateinit var mEtPassword: EditText
    @BindView(R2.id.btn_login)
    lateinit var mBtnLogin: Button

    override fun getLayoutId(): Int {
        return R.layout.wan_fragment_login
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_login, true)
    }

    @OnClick(R2.id.btn_login)
    fun onViewClicked() {
        val username = mEtUsername.text.toString()
        val password = mEtPassword.text.toString()
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "请输入密码！", Toast.LENGTH_SHORT).show()
            return
        }
        mActionCreator.login(username, password)
    }
}
