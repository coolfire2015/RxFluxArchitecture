package com.huyingbao.core.arch.dispatcher;

import com.huyingbao.core.arch.action.RxAction;
import com.huyingbao.core.arch.action.RxActionError;
import com.huyingbao.core.arch.store.RxStoreActionDispatch;
import com.huyingbao.core.arch.store.RxStoreChange;
import com.huyingbao.core.arch.view.RxFluxView;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.collection.ArrayMap;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Dispatcher不会被直接使用，
 * 而是通过通过一个ActionCreator来封装Dispatcher，
 * 并提供便捷的方法来分发View中产生的事件，
 * 消息的传递通过Action（Action是一个普通的POJO类）来封装。
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RxDispatcher {
    private final RxBus mRxBus;
    private ArrayMap<String, Disposable> mRxActionMap;
    private ArrayMap<String, Disposable> mRxStoreMap;

    @Inject
    public RxDispatcher(RxBus rxBus) {
        this.mRxBus = rxBus;
        this.mRxActionMap = new ArrayMap<>();
        this.mRxStoreMap = new ArrayMap<>();
    }

    /**
     * 需要将store注册到dispatcher中
     *
     * @param rxStore
     * @param <T>     实现RxActionDispatch的RxStore
     */
    public <T extends RxStoreActionDispatch> void subscribeRxStore(final T rxStore) {
        //获取对象的类名
        final String rxStoreTag = rxStore.getClass().getSimpleName();
        //获取key(类名)对应的value(Subscription)
        Disposable disposable = mRxActionMap.get(rxStoreTag);
        //如果订阅不为空或者订阅是取消状态,则进行订阅
        if (disposable == null || disposable.isDisposed()) {
            //filter过滤,传入一个Predicate类对象,参数Object,返回boolean,若是object是RxAction的子类实现,则返回true,执行订阅
            mRxActionMap.put(rxStoreTag, mRxBus.get()
                    .onBackpressureBuffer()
                    .filter(o -> o instanceof RxAction)
                    .observeOn(AndroidSchedulers.mainThread())
                    //Post RxAction (RxStore extends RxStoreActionDispatch)object调用onRxAction方法
                    .subscribe(o -> rxStore.onRxAction((RxAction) o)));
        }
    }

    /**
     * 注册view 到错误监听
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxFluxView> void subscribeRxError(final T rxView) {
        final String rxViewErrorTag = rxView.getClass().getSimpleName() + "_error";
        Disposable disposable = mRxActionMap.get(rxViewErrorTag);
        if (disposable == null || disposable.isDisposed()) {
            mRxActionMap.put(rxViewErrorTag, mRxBus.get()
                    .onBackpressureBuffer()
                    .filter(o -> o instanceof RxActionError)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> rxView.onRxError((RxActionError) o)));
        }
    }

    /**
     * Bus(Subject被监听者)发送一个事件到所有订阅bus(Subject)的监听者Subscription
     * 当该事件是RxStoreChange的实现类的时候,
     * 调用监听者Subscription的方法回调方法call
     * 添加RxViewDispatch到dispatch的订阅中.
     * view 对应的类名 当作 key
     * mRxBus 进行订阅,订阅事件当作 value
     * view 添加到rxStoreMap中之后,bus发送数据,在观察者中会回调rxViewDispatch的回调方法
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxFluxView> void subscribeRxView(final T rxView) {
        //获取传入的Object的名字
        final String rxViewTag = rxView.getClass().getSimpleName();
        //获取Map中Object名字对应的value 监听者
        Disposable disposable = mRxStoreMap.get(rxViewTag);
        //如果监听者空或者没订阅被监听者,生成一个新的监听者,并将他添加到 storeMap中
        if (disposable == null || disposable.isDisposed()) {
            //获取rxBus实例,是一个Observable(被监听者)的子类对象
            //Subject=new SerializedSubject<>(PublishSubject.create())
            //会把在订阅(subscribe())发生的时间点之后来自原始Observable的数据发射给观察者
            mRxStoreMap.put(rxViewTag, mRxBus.get()
                    .onBackpressureBuffer()
                    .filter(o -> o instanceof RxStoreChange)
                    .observeOn(AndroidSchedulers.mainThread())
                    //调用Activity,Fragment,View等所有实现了RxViewDispatch的类对象的onRxStoreChange方法
                    .subscribe(o -> rxView.onRxStoreChanged((RxStoreChange) o)));
        }
        subscribeRxError(rxView);
    }

    /**
     * 判断是否注册
     *
     * @param object
     * @param <T>
     * @return {@code true} object对应的Subscription不为空且已经注册, {@code false} otherwise
     */
    public <T extends RxFluxView> boolean isSubscribeRxView(final T object) {
        //获取传入的Object的名字
        final String tag = object.getClass().getSimpleName();
        //获取Map中Object名字对应的value 监听者
        Disposable disposable = mRxStoreMap.get(tag);
        return disposable != null && !disposable.isDisposed();
    }

    /**
     * 解除rxStore的注册
     *
     * @param object
     * @param <T>
     */
    public <T extends RxStoreActionDispatch> void unsubscribeRxStore(final T object) {
        String tag = object.getClass().getSimpleName();
        Disposable disposable = mRxActionMap.get(tag);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            mRxActionMap.remove(tag);
        }
    }

    /**
     * 解除错误注册
     *
     * @param object
     * @param <T>
     */
    public <T extends RxFluxView> void unsubscribeRxError(final T object) {
        String tag = object.getClass().getSimpleName() + "_error";
        Disposable disposable = mRxActionMap.get(tag);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            mRxActionMap.remove(tag);
        }
    }

    /**
     * 将view解除注册
     *
     * @param object
     * @param <T>
     */
    public <T extends RxFluxView> void unsubscribeRxView(final T object) {
        String tag = object.getClass().getSimpleName();
        Disposable disposable = mRxStoreMap.get(tag);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            mRxStoreMap.remove(tag);
        }
        unsubscribeRxError(object);
    }

    /**
     * 解除所有的注册
     */
    public synchronized void unsubscribeAll() {
        for (Disposable disposable : mRxActionMap.values())
            disposable.dispose();
        for (Disposable disposable : mRxStoreMap.values())
            disposable.dispose();
        mRxActionMap.clear();
        mRxStoreMap.clear();
    }

    /**
     * 1:发送action变化,到所有订阅的store
     *
     * @param action
     */
    public void postRxAction(final RxAction action) {
        mRxBus.send(action);
    }

    /**
     * 2:发送store变化
     *
     * @param storeChange
     */
    public void postRxStoreChange(final RxStoreChange storeChange) {
        mRxBus.send(storeChange);
    }
}
