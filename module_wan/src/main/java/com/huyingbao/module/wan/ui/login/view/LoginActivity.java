package com.huyingbao.module.wan.ui.login.view;

import android.content.Intent;
import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.wan.ui.article.view.ArticleActivity;
import com.huyingbao.module.wan.ui.login.action.LoginAction;
import com.huyingbao.module.wan.ui.login.store.LoginStore;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class LoginActivity extends CommonRxActivity<LoginStore> {
    @Inject
    Lazy<LoginFragment> mLoginFragmentLazy;

    @Override
    protected Fragment createFragment() {
        return mLoginFragmentLazy.get();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case LoginAction.LOGIN:
                startActivity(new Intent(this, ArticleActivity.class));
                finish();
                break;
        }
    }
}
