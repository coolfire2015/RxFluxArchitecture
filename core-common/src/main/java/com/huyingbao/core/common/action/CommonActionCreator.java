package com.huyingbao.core.common.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 公用的ActionCreator，主要使用其{@link RxActionCreator#postLocalChange(String)}等方法。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class CommonActionCreator extends RxActionCreator {
    @Inject
    public CommonActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }
}
