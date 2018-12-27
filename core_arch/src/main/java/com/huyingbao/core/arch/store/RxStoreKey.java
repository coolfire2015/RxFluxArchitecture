package com.huyingbao.core.arch.store;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

/**
 * Qualifier注解用来区分同一纬度下两种不同的创建实例的方法
 * 区分不同的RxStore子类 对ClassKey注解的扩展
 * Created by liujunfeng on 2017/12/7.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface RxStoreKey {
    Class<? extends ViewModel> value();
}
