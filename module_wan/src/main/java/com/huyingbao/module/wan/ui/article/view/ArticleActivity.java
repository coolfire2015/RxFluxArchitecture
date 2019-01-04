package com.huyingbao.module.wan.ui.article.view;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.module.wan.ui.friend.view.FriendFragment;
import com.huyingbao.module.wan.ui.login.view.LoginActivity;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Route(path = "/wan/ArticleActivity")
public class ArticleActivity extends CommonRxActivity<ArticleStore> {
    @Inject
    Lazy<ArticleListFragment> mArticleListFragmentLazy;
    @Inject
    Lazy<FriendFragment> mFriendFragmentLazy;
    @Inject
    Lazy<BannerFragment> mBannerFragmentLazy;

    @Nullable
    @Override
    public ArticleStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(ArticleStore.class);
    }

    @Override
    protected Fragment createFragment() {
        return mArticleListFragmentLazy.get();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case ArticleAction.TO_BANNER:
                addFragmentHideExisting(mBannerFragmentLazy.get());
                break;
            case ArticleAction.TO_FRIEND:
                addFragmentHideExisting(mFriendFragmentLazy.get());
                break;
            case ArticleAction.TO_LOGIN:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
