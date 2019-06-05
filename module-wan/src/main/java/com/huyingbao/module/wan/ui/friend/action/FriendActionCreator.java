package com.huyingbao.module.wan.ui.friend.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.scope.FragmentScope;
import com.huyingbao.module.wan.api.WanApi;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@FragmentScope
public class FriendActionCreator extends RxActionCreator implements FriendAction {
    private WanApi mWanApi;

    @Inject
    FriendActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager, WanApi wanApi) {
        super(rxDispatcher, rxActionManager);
        mWanApi = wanApi;
    }

    @Override
    public void getFriendList() {
        RxAction rxAction = newRxAction(GET_FRIEND_LIST);
        postHttpRetryAction(rxAction, mWanApi.getFriendList());
    }
}
