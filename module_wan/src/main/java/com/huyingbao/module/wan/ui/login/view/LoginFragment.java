package com.huyingbao.module.wan.ui.login.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.rxview.CommonRxFragment;
import com.huyingbao.core.common.util.CommonUtils;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.R2;
import com.huyingbao.module.wan.ui.login.action.LoginActionCreator;
import com.huyingbao.module.wan.ui.login.store.LoginStore;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@ActivityScope
public class LoginFragment extends CommonRxFragment<LoginStore> {
    @Inject
    LoginActionCreator mActionCreator;

    @BindView(R2.id.et_username)
    EditText mEtUsername;
    @BindView(R2.id.et_password)
    EditText mEtPassword;
    @BindView(R2.id.btn_login)
    Button mBtnLogin;

    @Inject
    public LoginFragment() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.wan_fragment_login;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTitle(R.string.wan_label_login, true);
    }

    @OnClick(R2.id.btn_login)
    public void login() {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        if (CommonUtils.isEmpty(username) || CommonUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "请输入密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        mActionCreator.login(username, password);
    }
}
