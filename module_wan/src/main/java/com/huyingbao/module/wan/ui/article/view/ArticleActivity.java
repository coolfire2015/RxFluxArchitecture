package com.huyingbao.module.wan.ui.article.view;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.rxview.CommonRxActivity;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.module.wan.ui.friend.view.FriendFragment;
import com.huyingbao.module.wan.ui.login.view.LoginActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import dagger.Lazy;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Route(path = "/wan/ArticleActivity")
public class ArticleActivity extends CommonRxActivity<ArticleStore> {
    @Inject
    Lazy<ArticleListFragment> mArticleListFragmentLazy;
    @Inject
    Lazy<FriendFragment> mFriendFragmentLazy;
    @Inject
    Lazy<BannerFragment> mBannerFragmentLazy;

    @Override
    protected Fragment createFragment() {
        return mArticleListFragmentLazy.get();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
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
            default:
                break;
        }
    }
}
