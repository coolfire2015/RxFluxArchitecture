package com.huyingbao.module.wan.ui.store;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxStoreForActivity;
import com.huyingbao.module.wan.ui.action.WanAction;
import com.huyingbao.module.wan.ui.model.GitRepo;
import com.huyingbao.module.wan.ui.model.GitUser;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class WanStore extends RxStoreForActivity {
    private final MutableLiveData<List<GitRepo>> mGitRepoList = new MutableLiveData<>();
    private final MutableLiveData<GitUser> mGitUser = new MutableLiveData<>();
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
        mGitUser.setValue(null);
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
            case WanAction.GET_GIT_USER:
                mGitUser.setValue(rxAction.get(RxActionCreator.RESPONSE));
                break;
            case WanAction.TO_GIT_USER:
                postChange(RxChange.newRxChange(rxAction.getTag()));
                break;
            default://此处不能省略，不是本模块的逻辑，直接返回，不发送RxStoreChange
                return;
        }
    }

    public MutableLiveData<List<GitRepo>> getGitRepoList() {
        return mGitRepoList;
    }

    public MutableLiveData<GitUser> getGitUser() {
        return mGitUser;
    }

    public boolean isCreated() {
        return mIsCreated;
    }
}
