package com.huyingbao.module.wan.ui.login.view;

import android.os.Bundle;

import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.wan.ui.login.store.LoginStore;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by liujunfeng on 2019/1/2.
 */
public class LoginFragment extends CommonRxFragment<LoginStore> {
    @Nullable
    @Override
    public LoginStore getRxStore() {
        return ViewModelProviders.of(getActivity(), mViewModelFactory).get(LoginStore.class);
    }

    @Override
    public int getLayoutId() {
        return ;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
