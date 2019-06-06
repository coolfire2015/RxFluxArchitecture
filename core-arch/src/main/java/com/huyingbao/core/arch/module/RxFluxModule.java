package com.huyingbao.core.arch.module;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.huyingbao.core.arch.store.RxStoreFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * 依赖注入仓库，提供 1.Application， 2.{@link ViewModelProvider.Factory}实现类{@link RxStoreFactory}
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Module
public abstract class RxFluxModule {
    @Singleton
    @Binds
    abstract Context bindApplication(Application application);

    @Singleton
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(RxStoreFactory rxStoreFactory);
}

