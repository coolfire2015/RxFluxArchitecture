package com.huyingbao.module.wan.ui.login.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.rxview.CommonRxFragment
import com.huyingbao.core.common.util.CommonUtils
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.R2
import com.huyingbao.module.wan.ui.login.action.LoginActionCreator
import com.huyingbao.module.wan.ui.login.store.LoginStore

import javax.inject.Inject

import butterknife.BindView
import butterknife.OnClick

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class LoginFragment @Inject
constructor() : CommonRxFragment<LoginStore>() {
    @Inject
    internal var mActionCreator: LoginActionCreator? = null

    @BindView(R2.id.et_username)
    internal var mEtUsername: EditText? = null
    @BindView(R2.id.et_password)
    internal var mEtPassword: EditText? = null
    @BindView(R2.id.btn_login)
    internal var mBtnLogin: Button? = null

    override fun getLayoutId(): Int {
        return R.layout.wan_fragment_login
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_login, true)
    }

    @OnClick(R2.id.btn_login)
    fun onViewClicked() {
        val username = mEtUsername!!.text.toString()
        val password = mEtPassword!!.text.toString()
        if (CommonUtils.isEmpty(username) || CommonUtils.isEmpty(password)) {
            Toast.makeText(activity, "请输入密码！", Toast.LENGTH_SHORT).show()
            return
        }
        mActionCreator!!.login(username, password)
    }
}
