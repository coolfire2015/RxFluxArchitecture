package com.huyingbao.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注androidx.lifecycle.LifecycleObserver的实现类，Application生命周期观察者
 * <p>
 * 与{@link AppLifecycleOwner}配合使用。
 * <p>
 * （主模块）一个{@link AppLifecycleOwner} 对 （子模块）多个{@link AppLifecycleObserver}
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface AppLifecycleObserver {
}