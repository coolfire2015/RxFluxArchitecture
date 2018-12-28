package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.module.wan.action.WanApi;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
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
        postHttpAction(action, mWanApi.getArticleList(page));
    }

    @Override
    public void getBannerList() {
        RxAction action = newRxAction(GET_BANNER_LIST);
        postHttpAction(action, mWanApi.getBannerList());
    }
}
