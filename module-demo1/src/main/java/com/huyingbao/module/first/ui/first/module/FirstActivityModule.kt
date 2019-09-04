package com.huyingbao.module.first.ui.first.module


import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.first.ui.first.view.FirstFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class FirstActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectFirstFragment(): FirstFragment

    @Module
    companion object {
        @JvmStatic
        @ActivityScope
        @Provides
        fun provideArrayList(): ArrayList<String> {
            return ArrayList()
        }
    }
}
