package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.wan.action.WanApi
import com.huyingbao.module.wan.action.WanResponse
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.model.Page
import java.util.*
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
    lateinit var mWanApi: WanApi

    override fun getArticleList(page: Int) {
        val action = newRxAction(ArticleAction.GET_ARTICLE_LIST)
        postHttpAction(action, mWanApi!!.getArticleList(page))
    }

    override fun getBannerList() {
        val action = newRxAction(ArticleAction.GET_BANNER_LIST)
        postHttpAction(action, mWanApi!!.bannerList)
    }
}
