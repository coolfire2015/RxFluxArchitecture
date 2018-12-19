package com.huyingbao.core.module;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.huyingbao.core.store.RxStoreFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class RxFluxModule {
    @Singleton
    @Binds
    abstract Context privateContext(Application application);

    /**
     * 提供ViewModelProvider.Factory的实现类RxStoreFactory
     *
     * @param rxStoreFactory
     * @return
     */
    @Singleton
    @Binds
    abstract ViewModelProvider.Factory privateViewModelFactory(RxStoreFactory rxStoreFactory);
}

