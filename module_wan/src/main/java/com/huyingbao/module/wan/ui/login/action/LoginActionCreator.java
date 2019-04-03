package com.huyingbao.module.wan.ui.login.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.wan.action.WanActionCreator;
import com.huyingbao.module.wan.action.WanApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@ActivityScope
public class LoginActionCreator extends WanActionCreator implements LoginAction {
    private WanApi mWanApi;

    @Inject
    LoginActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager, WanApi wanApi) {
        super(rxDispatcher, rxActionManager);
        mWanApi = wanApi;
    }

    @Override
    public void register(String username, String password, String repassword) {

    }

    @Override
    public void login(String username, String password) {
        RxAction rxAction = newRxAction(LOGIN);
        postHttpAction(rxAction, mWanApi.login(username, password).flatMap(verifyResponse()));
    }

    @Override
    public void getIdentify() {
        RxAction rxAction = newRxAction(GET_IDENTIFY);
        Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(60)
                .map(aLong -> 60 - aLong);
        postHttpAction(rxAction, observable);
    }
}
