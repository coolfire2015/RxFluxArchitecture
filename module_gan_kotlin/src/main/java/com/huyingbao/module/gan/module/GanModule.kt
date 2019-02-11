package com.huyingbao.module.gan.module

import com.google.gson.GsonBuilder
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.gan.action.GanApi
import com.huyingbao.module.gan.ui.main.store.MainStore
import com.huyingbao.module.gan.ui.main.view.MainActivity
import com.huyingbao.module.gan.ui.random.store.RandomStore
import com.huyingbao.module.gan.ui.random.view.RandomActivity

import javax.inject.Singleton

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module其实是一个简单工厂模式，Module里面的方法基本都是创建类实例的方法。
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class GanModule {

    /**
     * 创建类实例有2个维度可以创建：
     * 1:通过Module中的Provide注解或者Binds等注解来创建（以下简称Module维度）优先
     * 2:通过用Inject注解标注的构造函数来创建（以下简称Inject维度）
     *
     *
     * Qualifier注解用来区分同一纬度下两种不同的创建实例的方法，类似MapKey
     *
     *
     * Provider需要写明具体的实现，
     * Binds只是告诉Dagger2谁是谁实现的，
     *
     *
     * Dagger2会根据这些信息自动生成一个关键的Map。
     * key为ViewModel的Class，
     * value则为提供ViewModel实例的Provider对象，
     * 通过provider.get()方法就可以获取到相应的ViewModel对象。
     *
     * @param randomStore
     * @return
     */
    @Singleton
    @Binds//当参数和返回值类型相同时，将方法写成抽象方法，用Binds注解。
    @IntoMap//可以让Dagger2将多个元素依赖注入到Map之中
    @RxStoreKey(RandomStore::class)
    internal abstract fun bindRandomStore(randomStore: RandomStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(MainStore::class)
    internal abstract fun bindMainStore(mainStore: MainStore): ViewModel

    /**
     * ContributesAndroidInjector注解帮助我们生成方法的返回值类型（RandomActivity）的注射器
     * 自动生成注射器AndroidInjector子类RandomActivitySubcomponent
     *
     *
     * ContributesAndroidInjector用来简化Subcomponent的书写
     *
     *
     * 在2.11之前，每一个Fragment或者Activity都需要有自己的Component
     * 然后在Component中声明inject方法，
     * 最后在Fragment或者Activity中进行注入
     *
     *
     * ContributesAndroidInjector注解标注的方法必须是抽象方法
     * 并且返回值是具体的android framework的类型（比如Activity或者Fragment），
     * 此外，这个方法不能有参数
     *
     *
     * ActivityScope标注生成的Subcomponet生命周期跟随Activity
     * ContributesAndroidInjector指定DaggerAndroidActivity的Subcomponent中需要安装的Module
     *
     *
     * dagger.android会根据@ContributesAndroidInjector生成需要注入对应对象的SubComponent，
     * 并添加到map中
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = [GanActivityModule::class])
    internal abstract fun injectMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GanActivityModule::class])
    internal abstract fun injectRandomActivity(): RandomActivity

    companion object {
        @Singleton
        @Provides
        internal fun provideMainApi(client: OkHttpClient): GanApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(GanApi::class.java!!)
        }
    }
}
