package com.huyingbao.core.arch.module

import android.app.Application
import android.content.Context

import com.huyingbao.core.arch.store.RxStoreFactory

import javax.inject.Singleton

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class RxFluxModule {
    @Singleton
    @Binds
    internal abstract fun bindApplication(application: Application): Context

    /**
     * 提供ViewModelProvider.Factory的实现类RxStoreFactory
     *
     * @param rxStoreFactory
     * @return
     */
    @Singleton
    @Binds
    internal abstract fun bindViewModelFactory(rxStoreFactory: RxStoreFactory): ViewModelProvider.Factory
}

