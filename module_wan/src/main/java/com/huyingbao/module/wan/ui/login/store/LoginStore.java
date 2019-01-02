package com.huyingbao.module.wan.ui.login.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxStoreForActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2019/1/2.
 */
@Singleton
public class LoginStore extends RxStoreForActivity {
    @Inject
    LoginStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    @Override
    public void onRxAction(RxAction rxAction) {

    }
}
