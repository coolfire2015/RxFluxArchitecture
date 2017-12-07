package com.huyingbao.core.module;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.huyingbao.core.store.RxStoreFactory;

import dagger.Binds;
import dagger.Module;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class BaseModule {
    @Binds
    abstract Context privateContext(Application application);

    @Binds
    abstract ViewModelProvider.Factory privateViewModelFactory(RxStoreFactory rxStoreFactory);
}

