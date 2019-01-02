package com.huyingbao.module.wan.ui.tree.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxActivityStore;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2018/12/26.
 */
@Singleton
public class TreeStore extends RxActivityStore {
    @Inject
    TreeStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    @Override
    @Subscribe
    public void onRxAction(RxAction rxAction) {

    }
}
