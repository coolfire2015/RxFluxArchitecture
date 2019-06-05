package com.huyingbao.module.github


import com.huyingbao.core.base.BaseApp
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by liujunfeng on 2019/1/1.
 */
class GithubApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerGithubComponent.builder().application(this).build()
    }
}
