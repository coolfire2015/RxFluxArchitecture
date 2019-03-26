package com.huyingbao.core.common.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Singleton
public class CommonActionCreator extends RxActionCreator {
    @Inject
    public CommonActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }
}
