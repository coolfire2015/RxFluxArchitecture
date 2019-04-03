package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.module.wan.action.WanActionCreator;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.model.Page;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * rxAction创建发送管理类
 *
 * @author liujunfeng
 * @date 2019/1/1
 */
@Singleton
public class ArticleActionCreator extends WanActionCreator implements ArticleAction {
    private WanApi mWanApi;

    @Inject
    ArticleActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager, WanApi wanApi) {
        super(rxDispatcher, rxActionManager);
        mWanApi = wanApi;
    }

    @Override
    public void getArticleList(int page) {
        RxAction rxAction = newRxAction(GET_ARTICLE_LIST);
        //延迟5s调用接口，测试取消操作
        Observable<WanResponse<Page<Article>>> httpObservable = Observable
                .timer(5, TimeUnit.SECONDS)
                .flatMap(s -> mWanApi.getArticleList(page));
        postHttpLoadingAction(rxAction, httpObservable);
    }

    @Override
    public void getBannerList() {
        RxAction rxAction = newRxAction(GET_BANNER_LIST);
        //接口调用失败，自动重复调用5次，每次间隔3s
        Observable<WanResponse<ArrayList<Banner>>> httpObservable = mWanApi
                .getBannerList()
                .retryWhen(retryAction(5, 3))
                .flatMap(verifyResponse());
        postHttpAction(rxAction, httpObservable);
    }
}
