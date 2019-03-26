package com.huyingbao.module.wan.ui.friend.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.wan.action.WanApi;

import javax.inject.Inject;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@ActivityScope
public class FriendActionCreator extends RxActionCreator implements FriendAction {
    @Inject
    WanApi mWanApi;

    @Inject
    FriendActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void getFriendList() {
        RxAction action = newRxAction(GET_FRIEND_LIST);
        postHttpRetryAction(action, mWanApi.getFriendList());
    }
}
