package com.huyingbao.core.arch.utils

import android.app.Application

import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

import dagger.internal.Preconditions.checkNotNull

/**
 * Created by liujunfeng on 2019/1/1.
 */
object RxAndroidInjection {
    /**
     * 公用Dagger.Android依赖注入方法
     *
     * @param object      需要完成依赖注入的类
     * @param application 实现[HasAndroidInjector]的Application
     */
    fun inject(`object`: Any, application: Application) {
        val injector: AndroidInjector<Any>
        if (application is HasAndroidInjector) {
            injector = (application as HasAndroidInjector).androidInjector()
            checkNotNull(injector, "%s.androidInjector() returned null", application.javaClass)
        } else {
            throw RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            application.javaClass.getCanonicalName(),
                            HasAndroidInjector::class.java!!.getCanonicalName()))
        }
        injector.inject(`object`)
    }
}
