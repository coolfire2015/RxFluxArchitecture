package com.huyingbao.module.wan.ui.article.view;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;

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
@Route(path = "/git/ArticleActivity")
public class ArticleActivity extends CommonRxActivity<ArticleStore> {
    @Inject
    Lazy<ArticleListFragment> mGitRepoFragmentLazy;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Nullable
    @Override
    public ArticleStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(ArticleStore.class);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {

        }
    }

    @Override
    protected Fragment createFragment() {
        return mGitRepoFragmentLazy.get();
    }
}
