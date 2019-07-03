package com.huyingbao.module.github.module

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.main.store.MainStore
import com.huyingbao.module.github.ui.star.store.StarStore
import com.huyingbao.module.github.ui.user.store.UserStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Store仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class GithubStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(LoginStore::class)
    abstract fun bindLoginStore(loginStore: LoginStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(MainStore::class)
    abstract fun bindMainStore(mainStore: MainStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(UserStore::class)
    abstract fun bindUserStore(userStore: UserStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(StarStore::class)
    abstract fun bindStarStore(starStore: StarStore): ViewModel
}
