package com.huyingbao.module.git.ui.module;


import com.huyingbao.core.scope.PerFragment;
import com.huyingbao.module.git.ui.GitRepoFragment;
import com.huyingbao.module.git.ui.GitUserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class GitActivityModule {
    @PerFragment
    @ContributesAndroidInjector
    abstract GitUserFragment injectGitUserFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract GitRepoFragment injectGitRepoListFragment();
}
