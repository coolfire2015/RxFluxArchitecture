package com.huyingbao.module.wan.ui.action;

import android.content.Context;

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
public class WanActionCreator extends RxActionCreator implements WanAction {
    @Inject
    WanApi mWanApi;

    @Inject
    public WanActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void getGitRepoList() {
        RxAction action = newRxAction(GET_GIT_REPO_LIST);
        postHttpAction(action, mWanApi.getGitRepoList());
    }

    @Override
    public void gitGitUser(Context context, int userId) {
        RxAction action = newRxAction(GET_GIT_USER);
        postHttpAction(action, mWanApi.getGitUser(userId));
    }
}
