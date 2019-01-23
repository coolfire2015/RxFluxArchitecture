package com.huyingbao.module.kotlin.ui.article.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.kotlin.action.KotlinApi
import com.huyingbao.module.kotlin.action.KotlinResponse
import com.huyingbao.module.kotlin.ui.article.model.Article
import com.huyingbao.module.kotlin.ui.article.model.Banner
import com.huyingbao.module.kotlin.ui.article.model.Page

import javax.inject.Inject
import javax.inject.Singleton


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager), ArticleAction {
    @Inject
    internal var mKotlinApi: KotlinApi? = null

    override fun getArticleList(page: Int) {
        val action = newRxAction(ArticleAction.GET_ARTICLE_LIST)
        postHttpAction<KotlinResponse<Page<Article>>>(action, mKotlinApi!!.getArticleList(page))
    }

    override fun getBannerList() {
        val action = newRxAction(ArticleAction.GET_BANNER_LIST)
        postHttpAction<KotlinResponse<ArrayList<Banner>>>(action, mKotlinApi!!.bannerList)
    }
}
