package com.huyingbao.module.wan.ui.article.store;

import androidx.lifecycle.MutableLiveData;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;
import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.model.Page;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class ArticleStore extends RxActivityStore {
    private MutableLiveData<List<Article>> mArticleLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Banner>> mBannerLiveData = new MutableLiveData<>();
    /**
     * 列表页数
     */
    private int mNextRequestPage = 1;

    @Inject
    ArticleStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    @Override
    protected void onCleared() {
        mNextRequestPage = 1;
        mArticleLiveData.setValue(null);
        mBannerLiveData.setValue(null);
    }

    @Subscribe(tags = {ArticleAction.GET_BANNER_LIST})
    public void setBannerLiveData(RxAction rxAction) {
        WanResponse<ArrayList<Banner>> bannerResponse = rxAction.getResponse();
        mBannerLiveData.setValue(bannerResponse.getData());
    }

    public MutableLiveData<List<Banner>> getBannerLiveData() {
        return mBannerLiveData;
    }

    @Subscribe(tags = {ArticleAction.GET_ARTICLE_LIST})
    public void setArticleLiveData(RxAction rxAction) {
        WanResponse<Page<Article>> articleResponse = rxAction.getResponse();
        if (mArticleLiveData.getValue() == null) {
            mArticleLiveData.setValue(articleResponse.getData().getDatas());
        } else {
            mArticleLiveData.getValue().addAll(articleResponse.getData().getDatas());
            mArticleLiveData.setValue(mArticleLiveData.getValue());
        }
        mNextRequestPage++;
    }

    public MutableLiveData<List<Article>> getArticleLiveData() {
        return mArticleLiveData;
    }

    public int getNextRequestPage() {
        return mNextRequestPage;
    }

    public void setNextRequestPage(int nextRequestPage) {
        mNextRequestPage = nextRequestPage;
    }
}
