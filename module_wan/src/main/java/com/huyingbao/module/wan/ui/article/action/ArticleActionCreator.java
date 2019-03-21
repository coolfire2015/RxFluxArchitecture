package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Page;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class ArticleActionCreator extends RxActionCreator implements ArticleAction {
    @Inject
    WanApi mWanApi;

    @Inject
    ArticleActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void getArticleList(int page) {
        RxAction action = newRxAction(GET_ARTICLE_LIST);
        //延迟5s调用接口，测试取消操作
        Observable<WanResponse<Page<Article>>> article = Observable.just("Article")
                .delay(5, TimeUnit.SECONDS)
                .flatMap(s -> mWanApi.getArticleList(page));
        postLoadingHttpAction(action, article);
    }

    @Override
    public void getBannerList() {
        RxAction action = newRxAction(GET_BANNER_LIST);
        postHttpAction(action, mWanApi.getBannerList());
    }
}
