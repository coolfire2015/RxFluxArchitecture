package com.huyingbao.module.common.ui.update.module

import com.huyingbao.core.progress.DownloadApi
import com.huyingbao.core.progress.ProgressInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * [com.huyingbao.module.common.ui.update.view.CommonUpdateDialog]的依赖注入仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
@InstallIn(FragmentComponent::class)
class CommonUpdateDialogModule {
    @FragmentScoped
    @Provides
    fun provideDownloadApi(
            builder: OkHttpClient.Builder,
            progressInterceptor: ProgressInterceptor
    ): DownloadApi {
        //初始化OkHttp
        val client = builder
                .addInterceptor(progressInterceptor)
                .retryOnConnectionFailure(true)
                .build()
        //初始化Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.debug.com/")
                .client(client)
                .build()
        return retrofit.create(DownloadApi::class.java)
    }
}