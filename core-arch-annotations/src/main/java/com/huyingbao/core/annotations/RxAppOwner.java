package com.huyingbao.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注Application本身，生命周期持有者，被观察者
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RxAppOwner {
}