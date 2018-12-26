package com.huyingbao.module.wan.ui.tree.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;

/**
 * Created by liujunfeng on 2018/12/26.
 */
public class TreeActionCreator extends RxActionCreator implements TreeAction {
    public TreeActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }
}
