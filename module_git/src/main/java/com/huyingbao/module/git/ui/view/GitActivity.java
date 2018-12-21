package com.huyingbao.module.git.ui.view;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.common.util.ActivityUtils;
import com.huyingbao.core.common.view.CommonActivity;
import com.huyingbao.module.git.R;
import com.huyingbao.module.git.ui.action.GitAction;
import com.huyingbao.module.git.ui.module.GitStore;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Route(path = "/git/GitActivity")
public class GitActivity extends CommonActivity {
    @Inject
    Lazy<GitRepoFragment> mGitRepoFragmentLazy;
    @Inject
    Lazy<GitUserFragment> mGitUserFragmentLazy;

    private GitStore mStore;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(this, mViewModelFactory).get(GitStore.class);
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content) == null)
            ActivityUtils.addFragment(getSupportFragmentManager(), mGitRepoFragmentLazy.get(), R.id.fl_content);
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(mStore);
    }

    /**
     * 跳转用户页面
     */
    @Subscribe(tags = {@Tag(GitAction.TO_GIT_USER)})
    public void toGitUser(RxChange rxChange) {
        ActivityUtils.addAndHideFragment(getSupportFragmentManager(), mGitUserFragmentLazy.get(), R.id.fl_content);
    }
}
