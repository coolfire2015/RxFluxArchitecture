package com.huyingbao.module.gan.ui.category.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.scope.ActivityScope;

import javax.inject.Inject;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class CategoryActionCreator extends RxActionCreator implements CategoryAction {
    @Inject
    CategoryActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }
}
