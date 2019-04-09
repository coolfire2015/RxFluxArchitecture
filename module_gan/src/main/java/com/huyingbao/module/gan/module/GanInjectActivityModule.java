package com.huyingbao.module.gan.module;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.gan.ui.main.view.MainActivity;
import com.huyingbao.module.gan.ui.random.view.RandomActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2019/4/9.
 */
@Module
public abstract class GanInjectActivityModule {
    /**
     * ContributesAndroidInjector注解帮助我们生成方法的返回值类型（RandomActivity）的注射器
     * 自动生成注射器AndroidInjector子类RandomActivitySubcomponent
     * <p>
     * ContributesAndroidInjector用来简化Subcomponent的书写
     * <p>
     * 在2.11之前，每一个Fragment或者Activity都需要有自己的Component
     * 然后在Component中声明inject方法，
     * 最后在Fragment或者Activity中进行注入
     * <p>
     * ContributesAndroidInjector注解标注的方法必须是抽象方法
     * 并且返回值是具体的android framework的类型（比如Activity或者Fragment），
     * 此外，这个方法不能有参数
     * <p>
     * ActivityScope标注生成的Subcomponet生命周期跟随Activity
     * ContributesAndroidInjector指定DaggerAndroidActivity的Subcomponent中需要安装的Module
     * <p>
     * dagger.android会根据@ContributesAndroidInjector生成需要注入对应对象的SubComponent，
     * 并添加到map中
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = GanInjectFragmentModule.class)
    abstract MainActivity injectMainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = GanInjectFragmentModule.class)
    abstract RandomActivity injectRandomActivity();
}
