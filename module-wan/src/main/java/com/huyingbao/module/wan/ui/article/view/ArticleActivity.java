package com.huyingbao.module.wan.ui.article.view;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.base.rxview.BaseRxActivity;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.module.wan.ui.friend.view.FriendFragment;
import com.huyingbao.module.wan.ui.login.view.LoginActivity;

import org.greenrobot.eventbus.Subscribe;

import androidx.fragment.app.Fragment;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = "/wan/ArticleActivity")
public class ArticleActivity extends BaseRxActivity<ArticleStore> {
    @Override
    protected Fragment createFragment() {
        return ArticleListFragment.newInstance();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Subscribe(tags = {ArticleAction.TO_LOGIN}, sticky = true)
    public void toLogin(RxChange rxChange) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Subscribe(tags = {ArticleAction.TO_FRIEND}, sticky = true)
    public void toFriend(RxChange rxChange) {
        addFragmentHideExisting(FriendFragment.newInstance());
    }

    @Subscribe(tags = {ArticleAction.TO_BANNER}, sticky = true)
    public void toBanner(RxChange rxChange) {
        addFragmentHideExisting(BannerFragment.newInstance());
    }
}
