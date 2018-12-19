package com.huyingbao.core.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Java中，单例通常保存在一个静态域中，这样的单例往往要等到虚拟机关闭时候，该单例所占用的资源才释放。
 * 但是，Dagger通过ActivityScope创建出来的单例并不保持在静态域上，
 * 而是保留在Component(ActivityComponent)实例中
 * <p>
 * Scope是需要成对存在的
 * <p>
 * 在Module的Provide方法中使用了@Scope，那么对应的Component中也必须使用@Scope注解，
 * 当两边的@Scope名字一样时（比如同为@Singleton）,
 * 那么该Provide方法提供的依赖将会在Component中保持“局部单例”。
 * <p>
 * 1:依赖在Component中是单例的（供该依赖的provide方法和对应的Component类使用同一个Scope注解。
 * 2:对应的Component在App中只初始化一次，每次注入依赖都使用这个Component对象。
 * （在Application中创建该Component）
 * <p>
 * 正常情况下没有任何Scope标示的Component，注入器每次根据依赖都会新的实例
 * <p>
 * SubComponent必须使用和父Component不同的Scope标示
 * Created by liujunfeng on 2017/12/7.
 */
@Scope
@Retention(RUNTIME)//编译检测，防止不规范使用，SubComponent必须使用和父Component不同的Scope标示
public @interface ActivityScope {
}
