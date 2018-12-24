package com.huyingbao.module.wan.ui.view;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonFxActivity;
import com.huyingbao.module.wan.ui.action.WanAction;
import com.huyingbao.module.wan.ui.module.GitStore;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Route(path = "/git/GitFxActivity")
public class GitFxActivity extends CommonFxActivity<GitStore> {
    @Inject
    Lazy<GitRepoFragment> mGitRepoFragmentLazy;
    @Inject
    Lazy<GitUserFragment> mGitUserFragmentLazy;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Nullable
    @Override
    public GitStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(GitStore.class);
    }

    /**
     * 跳转用户页面
     */
    @Subscribe(tags = {@Tag(WanAction.TO_GIT_USER)})
    public void toGitUser(RxChange rxChange) {
        addFragmentHideExisting(mGitUserFragmentLazy.get());
    }

    @Override
    protected Fragment createFragment() {
        return mGitRepoFragmentLazy.get();
    }
}
