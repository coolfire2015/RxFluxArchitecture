package com.huyingbao.app;

import android.app.Application;

import com.huyingbao.core.module.CommonModule;
import com.huyingbao.module.gan.module.GanModule;
import com.huyingbao.module.git.action.GitModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * 在AppComponent中将dagger2库里的AndroidSupportInjectionModule注入到Application中，
 * 并将Application实现相应的接口（例如：HasActivityInjector、HasFragmentInjector、
 * HasServiceInjector、HasBroadcastReceiverInjector等等很多），
 * 并返回相应的方法，返回值参照以上App中的方式；
 * <p>
 * 注射器AndroidInjector子类
 * 该类是接口或抽象类。它在编译时会产生相应的类的实例来作为提供依赖方和需要依赖方之间的桥梁。
 * <p>
 * Component需要引用到目标类的实例，
 * Component会查找目标类中用Inject注解标注的属性，
 * 查找到相应的属性后会接着查找该属性对应的用Inject标注的构造函数（这时候就发生联系了），
 * 剩下的工作就是初始化该属性的实例并把实例进行赋值。
 * 因此我们也可以给Component叫另外一个名字注入器Injector，
 * 事实上Component必须继承的父类就叫做AndroidInjector
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
@Component(modules = {
        GanModule.class,
        GitModule.class,
        CommonModule.class,
        AndroidSupportInjectionModule.class})
public interface SimpleComponent extends AndroidInjector<SimpleApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        SimpleComponent.Builder application(Application application);

        SimpleComponent build();
    }
}
