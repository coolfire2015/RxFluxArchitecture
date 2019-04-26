package com.huyingbao.module.wan.ui.login.view;

import android.content.Intent;
import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.base.view.BaseActivity;
import com.huyingbao.module.wan.ui.article.view.ArticleActivity;
import com.huyingbao.module.wan.ui.login.action.LoginAction;
import com.huyingbao.module.wan.ui.login.store.LoginStore;

import org.greenrobot.eventbus.Subscribe;

import androidx.fragment.app.Fragment;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class LoginActivity extends BaseActivity<LoginStore> {
    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Subscribe(tags = {LoginAction.LOGIN}, sticky = true)
    public void onLogin(RxChange rxChange) {
        startActivity(new Intent(this, ArticleActivity.class));
        finish();
    }
}
