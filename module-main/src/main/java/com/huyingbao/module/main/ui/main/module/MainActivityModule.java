package com.huyingbao.module.main.ui.main.module;


import com.huyingbao.core.scope.PerFragment;
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
    @PerFragment
    @ContributesAndroidInjector
    abstract MainFragment injectMainFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract ShopFragment injectShopFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract ProductFragment injectProductListFragment();
}
