package com.huyingbao.module.wan.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.common.model.CommonHttpException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 模块自定义的ActionCreator
 * 对所有的接口添加返回结果检查
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class WanActionCreator extends RxActionCreator {

    public WanActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    protected <T> void postHttpAction(RxAction rxAction, Observable<T> httpObservable) {
        super.postHttpAction(rxAction, httpObservable.flatMap(verifyResponse()));
    }

    /**
     * 验证接口返回数据是正常
     * 1:没有数据,返回未知异常
     * 2:有数据,返回code不是成功码,返回自定义异常
     */
    private <T> Function<T, Observable<T>> verifyResponse() {
        return response -> {
            if (!(response instanceof WanResponse))
                return Observable.error(new CommonHttpException(600, "未知异常！"));
            int errorCode = ((WanResponse) response).getErrorCode();
            if (errorCode != 0) {
                String errorMsg = ((WanResponse) response).getErrorMsg();
                CommonHttpException exception = new CommonHttpException(errorCode, errorMsg);
                return Observable.error(exception);
            }
            return Observable.just(response);
        };
    }
}
