package com.huyingbao.module.gan.module;

import com.huyingbao.core.arch.store.RxStoreKey;
import com.huyingbao.module.gan.ui.main.store.MainStore;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by liujunfeng on 2019/4/9.
 */
@Module
public abstract class GanStoreModule {
    /**
     * 创建类实例有2个维度可以创建：
     * 1:通过Module中的Provide注解或者Binds等注解来创建（以下简称Module维度）优先
     * 2:通过用Inject注解标注的构造函数来创建（以下简称Inject维度）
     * <p>
     * Qualifier注解用来区分同一纬度下两种不同的创建实例的方法，类似MapKey
     * <p>
     * Provider需要写明具体的实现，
     * Binds只是告诉Dagger2谁是谁实现的，
     * <p>
     * Dagger2会根据这些信息自动生成一个关键的Map。
     * key为ViewModel的Class，
     * value则为提供ViewModel实例的Provider对象，
     * 通过provider.get()方法就可以获取到相应的ViewModel对象。
     * 注解Binds当参数和返回值类型相同时，将方法写成抽象方法,
     * 注解IntoMap可以让Dagger2将多个元素依赖注入到Map之中,
     *
     * @param randomStore
     * @return
     */
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(RandomStore.class)
    abstract ViewModel bindRandomStore(RandomStore randomStore);

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(MainStore.class)
    abstract ViewModel bindMainStore(MainStore mainStore);
}
