package com.huyingbao.module.github.module

import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * mock注入器,为测试代码提供,方便测试的全局对象
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [MockModule::class])
interface MockComponent {
    val retrofit: Retrofit
}
