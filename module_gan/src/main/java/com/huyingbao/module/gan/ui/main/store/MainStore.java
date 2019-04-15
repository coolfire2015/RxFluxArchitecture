package com.huyingbao.module.gan.ui.main.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.store.RxActivityStore;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class MainStore extends RxActivityStore {
    @Inject
    MainStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }
}
