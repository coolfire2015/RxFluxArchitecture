package com.huyingbao.module.wan.ui.login.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.utils.LocalStorageUtils;
import com.huyingbao.module.wan.action.WanActionCreator;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.action.WanContants;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

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
        postHttpLoadingAction(rxAction, mWanApi.register(username, password, repassword).flatMap(verifyResponse()));
    }

    @Override
    public void login(String username, String password) {
        RxAction rxAction = newRxAction(LOGIN);
        postHttpLoadingAction(rxAction, mWanApi.login(username, password).flatMap(verifyResponse()));
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
        postHttpLoadingAction(rxAction, coolfire2);
    }

    @Override
    public void changeBaseUrl(String baseUrl) {
        RxAction rxAction = newRxAction(CHANGE_BASE_URL);
        Single completable = Single.create(emitter -> {
            try {
                mLocalStorageUtils.setString(WanContants.Key.URL, baseUrl);
                emitter.onSuccess(baseUrl);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        postHttpAction(rxAction, completable.toObservable());
    }
}
