package com.huyingbao.core.arch.scope

import java.lang.annotation.Retention

import javax.inject.Scope

import java.lang.annotation.RetentionPolicy.RUNTIME

/**
 * FragmentScope创建出来的单例保留在FragmentComponent实例中
 * Created by liujunfeng on 2019/1/1.
 */
@Scope
@Retention(RUNTIME)
annotation class FragmentScope
