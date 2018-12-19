package com.huyingbao.module.gan.ui.category.module;


import com.huyingbao.core.scope.FragmentScope;
import com.huyingbao.module.gan.ui.category.view.CategoryListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class CategoryActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract CategoryListFragment injectCategoryListFragment();
}
