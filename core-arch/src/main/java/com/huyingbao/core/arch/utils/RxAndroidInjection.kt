package com.huyingbao.core.arch.utils

import dagger.android.HasAndroidInjector
import dagger.internal.Preconditions.checkNotNull

/**
 * Dagger.Android 中[dagger.android.AndroidInjection.inject]扩展为public
 *
 * Created by liujunfeng on 2019/1/1.
 */
object RxAndroidInjection {
    /**
     * Dagger.Android 中[dagger.android.AndroidInjection.inject]扩展为public
     *
     * @param target      需要被依赖注入的类
     */
    fun inject(target: Any, hasAndroidInjector: HasAndroidInjector) {
        val androidInjector = hasAndroidInjector.androidInjector()
        checkNotNull(
                androidInjector,
                "%s.androidInjector() returned null",
                hasAndroidInjector.javaClass)
        androidInjector.inject(target)
    }
}
