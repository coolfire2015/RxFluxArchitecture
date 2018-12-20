package com.huyingbao.module.git.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.store.RxStoreChange;
import com.huyingbao.core.common.util.ActivityUtils;
import com.huyingbao.core.common.view.CommonActivity;
import com.huyingbao.module.git.R;
import com.huyingbao.module.git.ui.module.GitActions;
import com.huyingbao.module.git.ui.module.GitStore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
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

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content) == null)
            ActivityUtils.addFragment(getSupportFragmentManager(), mGitRepoFragmentLazy.get(), R.id.fl_content);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getType()) {
            case GitActions.TO_GIT_USER:
                ActivityUtils.addAndHideFragment(getSupportFragmentManager(), mGitUserFragmentLazy.get(), R.id.fl_content);
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(ViewModelProviders.of(this, mViewModelFactory).get(GitStore.class));
    }
}
