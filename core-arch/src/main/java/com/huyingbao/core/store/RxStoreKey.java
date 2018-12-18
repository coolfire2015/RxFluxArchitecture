package com.huyingbao.core.store;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * 区分不同的RxStore子类
 * Created by liujunfeng on 2017/12/7.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface RxStoreKey {
    Class<? extends RxStore> value();
}
