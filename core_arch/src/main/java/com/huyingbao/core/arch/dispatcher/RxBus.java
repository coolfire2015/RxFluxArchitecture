package com.huyingbao.core.arch.dispatcher;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RxBus {
    private final FlowableProcessor<Object> mFlowableProcessor;

    @Inject
    public RxBus() {
        mFlowableProcessor = PublishProcessor.create().toSerialized();
    }

    public FlowableProcessor<Object> get() {
        return mFlowableProcessor;
    }

    public void send(Object o) {
        mFlowableProcessor.onNext(o);
    }

    /**
     * 返回指定类型的带背压的Flowable实例
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mFlowableProcessor.ofType(tClass);
    }

    public void unregisterAll() {
        mFlowableProcessor.onComplete();
    }

    /**
     * 是否已有观察者订阅
     *
     * @return
     */
    public boolean hasSubscribers() {
        return mFlowableProcessor.hasSubscribers();
    }
}
