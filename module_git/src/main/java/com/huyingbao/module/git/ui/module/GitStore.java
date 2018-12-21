package com.huyingbao.module.git.ui.module;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.module.git.ui.action.GitAction;
import com.huyingbao.module.git.ui.model.GitRepo;
import com.huyingbao.module.git.ui.model.GitUser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class GitStore extends RxStore {
    public final MutableLiveData<List<GitRepo>> mGitRepoList = new MutableLiveData<>();
    public final MutableLiveData<GitUser> mGitUser = new MutableLiveData<>();

    @Inject
    GitStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    /**
     * 接收需要跳转的页面的类别，并通知页面跳转
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(GitAction.GET_GIT_REPO_LIST)})
    public void setGitRepoList(RxAction action) {
        mGitRepoList.setValue(action.get(RxActionCreator.RESPONSE));
    }

    /**
     * 接收需要跳转的页面的类别，并通知页面跳转
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(GitAction.GET_GIT_USER)})
    public void receiveGitUser(RxAction action) {
        mGitUser.setValue(action.get(RxActionCreator.RESPONSE));
    }

    /**
     * 接收需要跳转的页面的类别，并通知页面跳转
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(GitAction.TO_GIT_USER)})
    public void receive(RxAction action) {
        postChange(RxChange.newRxChange(action.getTag()));
    }
}
