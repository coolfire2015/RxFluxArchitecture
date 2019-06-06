package com.huyingbao.core.arch.dispatcher;

import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.view.RxSubscriberView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 调度核心类：
 * 1.管理{@link com.huyingbao.core.arch.view.RxFluxView}、{@link RxStore}订阅。
 * 2.发送{@link RxAction}、{@link RxChange}、{@link RxLoading}、{@link RxError}、{@link RxRetry}。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class RxDispatcher {
    @Inject
    public RxDispatcher() {
    }

    /**
     * {@link RxStore}注册订阅。
     *
     * @param rxStore
     * @param <T>
     */
    public <T extends RxStore> void subscribeRxStore(final T rxStore) {
        EventBus.getDefault().register(rxStore);
    }

    /**
     * {@link com.huyingbao.core.arch.view.RxFluxView}注册订阅。
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxSubscriberView> void subscribeRxView(final T rxView) {
        EventBus.getDefault().register(rxView);
    }

    /**
     * {@link RxStore}取消订阅。
     *
     * @param rxStore
     * @param <T>
     */
    public <T extends RxStore> void unsubscribeRxStore(final T rxStore) {
        EventBus.getDefault().unregister(rxStore);
    }

    /**
     * {@link com.huyingbao.core.arch.view.RxFluxView}取消订阅。
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxSubscriberView> void unsubscribeRxView(final T rxView) {
        EventBus.getDefault().unregister(rxView);
    }

    /**
     * 判断是否注册订阅。
     *
     * @param object
     * @param <T>
     * @return {@code true}
     */
    public <T> boolean isSubscribe(final T object) {
        return EventBus.getDefault().isRegistered(object);
    }

    /**
     * 取消所有订阅。
     */
    public synchronized void unsubscribeAll() {
        EventBus.clearCaches();
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 发送{@link RxAction}到所有订阅的{@link RxStore}。
     */
    public void postRxAction(final RxAction rxAction) {
        EventBus.getDefault().post(rxAction, rxAction.getTag());
    }

    /**
     * 发送{@link RxChange}到所有订阅的{@link com.huyingbao.core.arch.view.RxFluxView}，粘性通知。
     */
    public void postRxChange(final RxChange rxChange) {
        EventBus.getDefault().postSticky(rxChange, rxChange.getTag());
    }

    /**
     * 发送{@link RxError}到所有订阅的{@link com.huyingbao.core.arch.view.RxFluxView}，粘性通知。
     * <p>
     * 发送：操作完成，异常执行状态。
     */
    public void postRxError(final RxError rxError) {
        EventBus.getDefault().postSticky(rxError);
    }

    /**
     * 发送{@link RxRetry}到所有订阅的{@link com.huyingbao.core.arch.view.RxFluxView}，粘性通知。
     * <p>
     * 发送：操作完成，异常执行状态，可重试。
     */
    public void postRxRetry(final RxRetry rxRetry) {
        EventBus.getDefault().postSticky(rxRetry);
    }

    /**
     * 发送{@link RxLoading}到所有订阅的{@link com.huyingbao.core.arch.view.RxFluxView}，粘性通知。
     * <p>
     * 发送：操作进度。
     */
    public void postRxLoading(final RxLoading rxLoading) {
        EventBus.getDefault().postSticky(rxLoading);
    }
}
