package com.huyingbao.module.wan.ui.login.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.rxview.CommonRxFragment;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.R2;
import com.huyingbao.module.wan.ui.login.action.LoginActionCreator;
import com.huyingbao.module.wan.ui.login.store.LoginStore;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liujunfeng on 2019/1/1.
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
    @BindView(R2.id.btn_identify)
    Button mBtnIdentify;

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
        getRxStore().getIntervalLiveData().observe(this, interval -> {
            if (TextUtils.isEmpty(interval) || TextUtils.equals(interval, "0")) {
                mBtnIdentify.setEnabled(true);
                mBtnIdentify.setText(R.string.wan_info_identify);
            } else {
                mBtnIdentify.setEnabled(false);
                String infoTimeout = getResources().getString(R.string.wan_info_timeout);
                mBtnIdentify.setText(String.format(infoTimeout, interval));
            }
        });
    }

    @OnClick(R2.id.btn_login)
    public void login() {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "请输入密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        mActionCreator.login(username, password);
        mActionCreator.getIdentify();
    }


    @OnClick(R2.id.btn_identify)
    public void identify() {
        mBtnIdentify.setEnabled(false);
        mActionCreator.getIdentify();
    }
}
