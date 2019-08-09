package com.huyingbao.module.common.module

import com.huyingbao.core.common.module.CommonModule
import dagger.Component
import dagger.Module
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 依赖注入器,为测试代码提供方便测试的全局对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
@Singleton
@Component(modules = [MockModule::class])
interface MockComponent {
    val retrofit: Retrofit
}

/**
 * 依赖注入仓库
 *
 * 1.提供[org.mockito.Spy]和[org.mockito.Mock]对象
 *
 * 2.提供测试代码需要的全局对象
 */
@Module(includes = [CommonModule::class])
class MockModule {

}
