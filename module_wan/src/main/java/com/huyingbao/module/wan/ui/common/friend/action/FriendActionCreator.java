package com.huyingbao.module.wan.ui.common.friend.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.wan.action.WanApi;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2018/12/26.
 */
@ActivityScope
public class FriendActionCreator extends RxActionCreator implements FriendAction {
    @Inject
    WanApi mApi;
    @Inject
    FriendActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void getFriendList() {

    }
}
