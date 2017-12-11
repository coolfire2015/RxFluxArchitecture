package com.huyingbao.module.main.ui.main.module;


import com.huyingbao.core.scope.FragmentScope;
import com.huyingbao.module.main.ui.main.MainFragment;
import com.huyingbao.module.main.ui.main.ProductFragment;
import com.huyingbao.module.main.ui.main.ShopFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract MainFragment injectMainFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract ShopFragment injectShopFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract ProductFragment injectProductListFragment();
}
