package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.arch.action.FluxActionCreator
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.wan.BuildConfig
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


/**
 * rxAction创建发送管理类
 *
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityRetainedScoped
class ArticleActionCreator @Inject constructor(
    @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : FluxActionCreator(), ArticleAction {

    override fun getArticleList(page: Int) {
        val rxAction = newAction(
            ArticleAction.GET_ARTICLE_LIST,
            CommonAppConstants.Key.PAGE, page
        )
        rxAction.isGlobalCatch = false
        val articleApi = retrofit.create(ArticleApi::class.java)
        postHttpLoadingAction(rxAction, flow { emit(articleApi.getArticleList(page)) })
    }

    override fun getBannerList() {
        val rxAction = newAction(ArticleAction.GET_BANNER_LIST)
        rxAction.isGlobalCatch = false
        postHttpLoadingAction(
            rxAction,
            flow { emit(retrofit.create(ArticleApi::class.java).getBannerList()) })
    }
}
