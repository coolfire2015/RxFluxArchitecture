package com.huyingbao.module.gan.ui.shop.module;


import com.huyingbao.core.scope.FragmentScope;
import com.huyingbao.module.gan.ui.shop.ShopFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class ShopActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract ShopFragment injectShopFragment();
}
