package com.huyingbao.module.git.module;


import com.huyingbao.core.arch.scope.FragmentScope;
import com.huyingbao.module.git.ui.view.GitRepoFragment;
import com.huyingbao.module.git.ui.view.GitUserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class GitActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract GitUserFragment injectGitUserFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract GitRepoFragment injectGitRepoListFragment();
}
