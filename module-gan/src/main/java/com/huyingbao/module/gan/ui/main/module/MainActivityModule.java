package com.huyingbao.module.gan.ui.main.module;


import com.huyingbao.core.scope.FragmentScope;
import com.huyingbao.module.gan.ui.main.GanFragment;
import com.huyingbao.module.gan.ui.main.ProductFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract GanFragment injectMainFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract ProductFragment injectProductListFragment();
}
