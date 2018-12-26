package com.huyingbao.module.wan.ui.login.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2018/12/26.
 */
@ActivityScope
public class LoginActionCreator extends RxActionCreator implements LoginAction {
    @Inject
    LoginActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void register(String username, String password, String repassword) {

    }

    @Override
    public void login(String username, String password) {

    }
}
