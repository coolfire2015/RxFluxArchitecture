package com.huyingbao.module.common.module

import dagger.Module

/**
 * 全局仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [LoadingInjectActivityModule::class, LoadingStoreModule::class])
class LoadingAppModule
