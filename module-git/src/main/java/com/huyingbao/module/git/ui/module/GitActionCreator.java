package com.huyingbao.module.git.ui.module;

import android.content.Context;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.dispatcher.RxDispatcher;
import com.huyingbao.core.action.RxActionManager;
import com.huyingbao.module.git.action.GitApi;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class GitActionCreator extends RxActionCreator implements GitActions {
    @Inject
    GitApi mGitApi;

    @Inject
    public GitActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void getGitRepoList() {
        RxAction action = newRxAction(GET_GIT_REPO_LIST);
        postHttpAction(action, mGitApi.getGitRepoList());
    }

    @Override
    public void gitGitUser(Context context, int userId) {
        RxAction action = newRxAction(GET_GIT_USER);
        postHttpAction(action, mGitApi.getGitUser(userId));
    }
}
