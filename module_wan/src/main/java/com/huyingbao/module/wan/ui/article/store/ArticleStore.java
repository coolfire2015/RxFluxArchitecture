package com.huyingbao.module.wan.ui.article.store;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxStoreForActivity;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;
import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.model.Page;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class ArticleStore extends RxStoreForActivity {
    private final MutableLiveData<WanResponse<Page<Article>>> mArticleLiveData = new MutableLiveData<>();
    private final MutableLiveData<WanResponse<ArrayList<Banner>>> mBannerLiveData = new MutableLiveData<>();
    private boolean mIsCreated;

    @Inject
    ArticleStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mIsCreated = false;
        mArticleLiveData.setValue(null);
    }

    /**
     * 接收RxAction
     * 处理RxAction携带的数据
     * 发送RxChange通知RxView
     *
     * @param rxAction
     */
    @Subscribe()
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getTag()) {
            case ArticleAction.GET_ARTICLE_LIST:
                mArticleLiveData.setValue(rxAction.get(RxActionCreator.RESPONSE));
                break;
            case ArticleAction.GET_BANNER_LIST:
                mBannerLiveData.setValue(rxAction.get(RxActionCreator.RESPONSE));
                break;
        }
    }

    public MutableLiveData<WanResponse<Page<Article>>> getArticleLiveData() {
        return mArticleLiveData;
    }

    public MutableLiveData<WanResponse<ArrayList<Banner>>> getBannerLiveData() {
        return mBannerLiveData;
    }

    public boolean isCreated() {
        return mIsCreated;
    }
}
