package com.huyingbao.module.wan.ui.login.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.util.LocalStorageUtils;
import com.huyingbao.module.wan.action.WanActionCreator;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.action.WanContants;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
public class LoginActionCreator extends WanActionCreator implements LoginAction {
    @Inject
    LocalStorageUtils mLocalStorageUtils;

    private WanApi mWanApi;

    @Inject
    LoginActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager, WanApi wanApi) {
        super(rxDispatcher, rxActionManager);
        mWanApi = wanApi;
    }

    @Override
    public void register(String username, String password, String repassword) {
        RxAction rxAction = newRxAction(REGISTER);
        postHttpAction(rxAction, mWanApi.register(username, password, repassword).flatMap(verifyResponse()));
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
                .take(10)
                .map(aLong -> 9 - aLong);
        Observable<Long> coolfire2 = mWanApi.login("coolfire", "123456")
                .flatMap(verifyResponse())
                .flatMap(userWanResponse -> observable);
        postHttpAction(rxAction, coolfire2);
    }

    @Override
    public void changeBaseUrl(String baseUrl) {
        RxAction rxAction = newRxAction(CHANGE_BASE_URL);
        Completable completable = Completable.create(emitter -> {
            try {
                mLocalStorageUtils.setString(WanContants.Key.URL, baseUrl);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        postHttpAction(rxAction, completable.toObservable());
    }
}
