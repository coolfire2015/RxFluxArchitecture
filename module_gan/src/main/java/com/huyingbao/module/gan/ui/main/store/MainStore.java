package com.huyingbao.module.gan.ui.main.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.module.gan.ui.main.action.MainAction;

import org.greenrobot.eventbus.Subscribe;

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

    /**
     * 接收RxAction
     * 处理RxAction携带的数据
     * 发送RxChange通知RxView
     *
     * @param rxAction
     */
    @Override
    @Subscribe()
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getTag()) {
            case MainAction.TO_WAN_MODULE:
            case MainAction.TO_GAN_MODULE:
                postChange(RxChange.newInstance(rxAction));
                break;
        }
    }
}
