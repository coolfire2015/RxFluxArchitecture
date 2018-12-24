package com.huyingbao.module.wan.ui.module;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.module.wan.ui.action.WanAction;
import com.huyingbao.module.wan.ui.model.GitRepo;
import com.huyingbao.module.wan.ui.model.GitUser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class GitStore extends RxStore {
    private final MutableLiveData<List<GitRepo>> mGitRepoList = new MutableLiveData<>();
    private final MutableLiveData<GitUser> mGitUser = new MutableLiveData<>();
    private boolean mIsCreated;

    @Inject
    GitStore(RxDispatcher rxDispatcher) {
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
        Logger.e("store cleared");
    }

    /**
     * 接收需要跳转的页面的类别，并通知页面跳转
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(WanAction.GET_ARTICLE_LIST)})
    public void setGitRepoList(RxAction action) {
        mGitRepoList.setValue(action.get(RxActionCreator.RESPONSE));
    }

    /**
     * 接收需要跳转的页面的类别，并通知页面跳转
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(WanAction.GET_GIT_USER)})
    public void receiveGitUser(RxAction action) {
        mGitUser.setValue(action.get(RxActionCreator.RESPONSE));
    }

    /**
     * 接收需要跳转的页面的类别，并通知页面跳转
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(WanAction.TO_GIT_USER)})
    public void receive(RxAction action) {
        postChange(RxChange.newRxChange(action.getTag()));
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
