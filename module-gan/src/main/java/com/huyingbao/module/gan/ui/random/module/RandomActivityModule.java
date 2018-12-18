package com.huyingbao.module.gan.ui.random.module;


import com.huyingbao.core.scope.FragmentScope;
import com.huyingbao.module.gan.ui.random.Fragment;
import com.huyingbao.module.gan.ui.random.ProductFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class RandomActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract Fragment injectGanFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract ProductFragment injectProductListFragment();
}
