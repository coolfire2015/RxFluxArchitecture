package com.huyingbao.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注Application生命周期观察者
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RxAppObserver {
}