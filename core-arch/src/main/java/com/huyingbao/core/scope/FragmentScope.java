package com.huyingbao.core.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * FragmentScope创建出来的单例保留在FragmentComponent实例中
 * Created by liujunfeng on 2017/12/7.
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
