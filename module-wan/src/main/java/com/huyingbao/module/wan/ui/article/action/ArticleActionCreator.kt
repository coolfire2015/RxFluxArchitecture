package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.wan.app.WanActionCreator
import io.reactivex.Observable
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * rxAction创建发送管理类
 *
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class ArticleActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : WanActionCreator(rxDispatcher, rxActionManager), ArticleAction {

    override fun getArticleList(page: Int) {
        val rxAction = newRxAction(ArticleAction.GET_ARTICLE_LIST)
        //延迟5s调用接口，测试取消操作
        val articleApi = retrofit.create(ArticleApi::class.java)
        val httpObservable = Observable.timer(5, TimeUnit.SECONDS).flatMap { articleApi.getArticleList(page) }
        postHttpLoadingAction(rxAction, articleApi.getArticleList(page))
    }

    override fun getBannerList() {
        val rxAction = newRxAction(ArticleAction.GET_BANNER_LIST)
        //接口调用失败，自动重复调用5次，每次间隔3s
        val httpObservable = retrofit.create(ArticleApi::class.java)
                .getBannerList()
                .retryWhen(retryAction(5, 3))
                .flatMap(verifyResponse())
        postHttpAction(rxAction, httpObservable)
    }
}
