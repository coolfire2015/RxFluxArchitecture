package com.huyingbao.module.wan.ui.login.view;

import android.os.Bundle;

import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.wan.ui.login.store.LoginStore;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2019/1/2.
 */
public class LoginActivity extends CommonRxActivity<LoginStore> {
    @Inject
    Lazy<LoginFragment> mLoginFragmentLazy;

    @Nullable
    @Override
    public LoginStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(LoginStore.class);
    }

    @Override
    protected Fragment createFragment() {
        return mLoginFragmentLazy.get();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
