package com.huyingbao.module.gan.ui.category.store;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.dispatcher.RxDispatcher;
import com.huyingbao.core.store.RxStore;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class CategoryStore extends RxStore {

    @Inject
    CategoryStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            default://此处不能省略，不是本模块的逻辑，直接返回，不发送RxStoreChange
                return;
        }
        //postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
