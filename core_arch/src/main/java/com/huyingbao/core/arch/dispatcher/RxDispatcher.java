package com.huyingbao.core.arch.dispatcher;

import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.view.RxFluxView;
import com.hwangjr.rxbus.RxBus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Dispatcher不会被直接使用，
 * 而是通过通过一个ActionCreator来封装Dispatcher，
 * 并提供便捷的方法来分发View中产生的事件，
 * 消息的传递通过Action（Action是一个普通的POJO类）来封装。
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RxDispatcher {
    @Inject
    public RxDispatcher() {
    }

    /**
     * 将store注册到rxbus中
     *
     * @param rxStore
     * @param <T>     实现RxActionDispatch的RxStore
     */
    public <T extends RxStore> void subscribeRxStore(final T rxStore) {
        RxBus.get().register(rxStore);
    }

    /**
     * 将view注册到rxbus中
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxFluxView> void subscribeRxView(final T rxView) {
        RxBus.get().register(rxView);
    }

    /**
     * 解除rxStore的注册
     *
     * @param rxStore
     * @param <T>
     */
    public <T extends RxStore> void unsubscribeRxStore(final T rxStore) {
        RxBus.get().unregister(rxStore);
    }

    /**
     * 将view解除注册
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxFluxView> void unsubscribeRxView(final T rxView) {
        RxBus.get().unregister(rxView);
    }

    /**
     * TODO 判断是否注册
     *
     * @param object
     * @param <T>
     * @return {@code true} object对应的Subscription不为空且已经注册, {@code false} otherwise
     */
    public <T extends RxFluxView> boolean isSubscribeRxView(final T object) {
        return false;
    }

    /**
     * TODO 解除所有的注册
     */
    public synchronized void unsubscribeAll() {
    }

    /**
     * 1:发送action变化,到所有订阅的store
     *
     * @param action
     */
    public void postRxAction(final RxAction action) {
        RxBus.get().post(action.getTag(), action);
    }

    /**
     * 2:发送store变化
     *
     * @param rxChange
     */
    public void postRxChange(final RxChange rxChange) {
        RxBus.get().post(rxChange.getTag(), rxChange);
    }

    /**
     * 3:发送action变化,到所有订阅的store
     *
     * @param rxError
     */
    public void postRxError(final RxError rxError) {
        RxBus.get().post(rxError.getTag(), rxError);
    }
}
