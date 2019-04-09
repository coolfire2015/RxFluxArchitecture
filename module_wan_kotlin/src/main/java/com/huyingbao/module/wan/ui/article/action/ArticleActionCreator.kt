package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.wan.action.WanActionCreator
import com.huyingbao.module.wan.action.WanApi
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : WanActionCreator(rxDispatcher, rxActionManager), ArticleAction {
    @Inject
    lateinit var mWanApi: WanApi

    override fun getArticleList(page: Int) {
        val action = newRxAction(ArticleAction.GET_ARTICLE_LIST)
        //延迟5s调用接口，测试取消操作
        val article = Observable.just("Article")
                .delay(5, TimeUnit.SECONDS)
                .flatMap { s -> mWanApi.getArticleList(page) }
        postHttpLoadingAction(action, article)
    }

    override fun getBannerList() {
        val action = newRxAction(ArticleAction.GET_BANNER_LIST)
        //接口调用失败，自动重复调用5次，每次间隔3s
        val httpObservable = mWanApi.bannerList
                .retryWhen(retryAction(5, 3))
        postHttpAction(action, httpObservable)
    }
}
