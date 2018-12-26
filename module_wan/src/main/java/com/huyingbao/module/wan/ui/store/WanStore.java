package com.huyingbao.module.wan.ui.store;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxStoreForActivity;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.action.WanAction;
import com.huyingbao.module.wan.ui.model.Article;
import com.huyingbao.module.wan.ui.model.Page;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class WanStore extends RxStoreForActivity {
    private final MutableLiveData<WanResponse<Page<Article>>> mGitRepoList = new MutableLiveData<>();
    private boolean mIsCreated;

    @Inject
    WanStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mIsCreated = false;
        mGitRepoList.setValue(null);
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
            case WanAction.GET_ARTICLE_LIST:
                mGitRepoList.setValue(rxAction.get(RxActionCreator.RESPONSE));
                break;
        }
    }

    public MutableLiveData<WanResponse<Page<Article>>> getGitRepoList() {
        return mGitRepoList;
    }

    public boolean isCreated() {
        return mIsCreated;
    }
}
