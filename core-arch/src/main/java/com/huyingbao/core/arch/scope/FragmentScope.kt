package com.huyingbao.core.arch.scope

import javax.inject.Scope

/**
 * [FragmentScope]创建出来的单例保留在FragmentComponent实例中
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope
