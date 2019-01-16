package com.huyingbao.module.gan.module;


import com.huyingbao.core.arch.scope.FragmentScope;
import com.huyingbao.module.gan.ui.main.view.MainFragment;
import com.huyingbao.module.gan.ui.random.view.CategoryFragment;
import com.huyingbao.module.gan.ui.random.view.ProductFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class GanActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract MainFragment injectMainFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract CategoryFragment injectCategoryFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract ProductFragment injectProductFragment();
}
