package com.huyingbao.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于检索@RxAppDelegate注解类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RxIndex {
    String[] modules() default {};
}
