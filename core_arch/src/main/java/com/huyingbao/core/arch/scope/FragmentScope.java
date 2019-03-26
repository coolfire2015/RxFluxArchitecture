package com.huyingbao.core.arch.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * FragmentScope创建出来的单例保留在FragmentComponent实例中
 *
 * @author liujunfeng
 * @date 2019/1/1
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
