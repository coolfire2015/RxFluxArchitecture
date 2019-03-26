package com.huyingbao.module.wan.ui.friend.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxFragmentStore;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.friend.action.FriendAction;
import com.huyingbao.module.wan.ui.friend.model.WebSite;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Singleton
public class FriendStore extends RxFragmentStore {
    private final MutableLiveData<WanResponse<ArrayList<WebSite>>> mWebSiteListData = new MutableLiveData<>();
    private boolean mIsCreated;

    @Inject
    FriendStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mIsCreated = false;
        mWebSiteListData.setValue(null);
    }

    @Override
    @Subscribe
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getTag()) {
            case FriendAction.GET_FRIEND_LIST:
                mIsCreated = true;
                mWebSiteListData.setValue(rxAction.getResponse());
                break;
            default:
                break;
        }
    }

    public MutableLiveData<WanResponse<ArrayList<WebSite>>> getWebSiteListData() {
        return mWebSiteListData;
    }

    public boolean isCreated() {
        return mIsCreated;
    }
}
