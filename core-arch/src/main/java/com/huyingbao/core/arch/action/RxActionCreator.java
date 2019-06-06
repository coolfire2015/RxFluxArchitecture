package com.huyingbao.core.arch.action;

import androidx.annotation.NonNull;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 实现交互方法，创建{@link RxAction}，直接发送给{@link com.huyingbao.core.arch.store.RxStore}。
 * <p>
 * 创建{@link RxChange}、{@link RxLoading}、{@link RxError}、{@link RxRetry}，直接发送给{@link com.huyingbao.core.arch.view.RxFluxView}。
 * <p>
 * 类似MVP模式中的Presenter，只是方法执行完成没有回调方法。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxActionCreator {
    private final RxDispatcher mRxDispatcher;
    private final RxActionManager mRxActionManager;

    public RxActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        this.mRxDispatcher = rxDispatcher;
        this.mRxActionManager = rxActionManager;
    }

    /**
     * IO线程运行被观察者{@link Observable}方法，
     * 主线程观察者{@link io.reactivex.Observer}接收并封装方法结果到{@link RxAction}。
     * 关联{@link RxAction}与{@link Disposable}。
     *
     * @param canShowLoading true:有进度显示,false:无进度显示
     * @param canRetry       true:操作异常可重试,false:操作异常抛异常
     */
    private <T> void postRxAction(RxAction rxAction, Observable<T> httpObservable, boolean canShowLoading, boolean canRetry) {
        if (hasRxAction(rxAction)) {
            return;
        }
        Disposable subscribe = httpObservable
                //指定IO线程
                .subscribeOn(Schedulers.io())
                // 调用开始
                .doOnSubscribe(subscription -> {
                    if (canShowLoading) {
                        //发送RxLoading(显示)事件
                        postRxLoading(rxAction.getTag(), true);
                    }
                })
                // 调用结束
                .doAfterTerminate(() -> {
                    if (canShowLoading) {
                        //发送RxLoading(消失)事件
                        postRxLoading(rxAction.getTag(), false);
                    }
                })
                // 操作结束,在io线程中接收接收反馈,没有切换线程
                .subscribe(
                        //操作进行中
                        response -> {
                            rxAction.setResponse(response);
                            postRxAction(rxAction);
                        },
                        //操作异常
                        throwable -> {
                            if (canRetry) {
                                postRxRetry(rxAction.getTag(), httpObservable, throwable);
                            } else {
                                postRxError(rxAction.getTag(), throwable);
                            }
                            removeRxAction(rxAction);
                        },
                        //操作结束
                        () -> {
                            removeRxAction(rxAction);
                        }
                );
        addRxAction(rxAction, subscribe);
    }

    /**
     * {@link RxActionManager}是否已存在{@link RxAction}
     */
    protected boolean hasRxAction(RxAction rxAction) {
        return mRxActionManager.contains(rxAction);
    }

    /**
     * {@link RxActionManager}添加{@link RxAction}和{@link Disposable}
     */
    protected void addRxAction(RxAction rxAction, Disposable disposable) {
        mRxActionManager.add(rxAction, disposable);
    }

    /**
     * {@link RxActionManager}移除{@link RxAction}，停止对应的{@link Disposable}，
     * 被观察者{@link Observable}正在运行的方法会被停止。
     */
    protected void removeRxAction(RxAction rxAction) {
        mRxActionManager.remove(rxAction);
    }

    /**
     * {@link RxDispatcher}发送{@link RxAction}
     */
    protected void postRxAction(RxAction rxAction) {
        mRxDispatcher.postRxAction(rxAction);
    }

    /**
     * {@link RxDispatcher}发送{@link RxLoading}
     *
     * @param isLoading true:显示，false:消失
     */
    protected void postRxLoading(String tag, boolean isLoading) {
        mRxDispatcher.postRxLoading(RxLoading.newInstance(tag, isLoading));
    }

    /**
     * {@link RxDispatcher}发送{@link RxRetry}
     */
    protected <T> void postRxRetry(String tag, Observable<T> httpObservable, Throwable throwable) {
        mRxDispatcher.postRxRetry(RxRetry.newInstance(tag, throwable, httpObservable));
    }

    /**
     * {@link RxDispatcher}发送{@link RxError}
     */
    protected void postRxError(String tag, Throwable throwable) {
        mRxDispatcher.postRxError(RxError.newInstance(tag, throwable));
    }

    /**
     * {@link RxDispatcher}发送{@link RxChange}
     */
    protected void postRxChange(RxChange rxChange) {
        mRxDispatcher.postRxChange(rxChange);
    }


    /**
     * 创建新的{@link RxAction}
     *
     * @param tag  tag对应具体是什么样的方法
     * @param data 键值对型的参数pair-value(String-Object))
     */
    protected RxAction newRxAction(@NonNull String tag, Object... data) {
        if (data != null) {
            if (data.length % 2 != 0) {
                throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
            }
        }
        RxAction.Builder actionBuilder = new RxAction.Builder(tag);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.put(key, value);
        }
        return actionBuilder.build();
    }

    /**
     * 异步执行，成功发送{@link RxAction}，异常发送{@link RxError}。
     */
    protected <T> void postHttpAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, false, false);
    }

    /**
     * 异步执行，成功发送{@link RxAction}，异常发送{@link RxError}。
     * 开始结束发送{@link RxLoading}。
     */
    protected <T> void postHttpLoadingAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, true, false);
    }

    /**
     * 异步执行，成功发送{@link RxAction}，异常发送{@link RxRetry}。
     */
    protected <T> void postHttpRetryAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, false, true);
    }

    /**
     * 异步执行，成功发送{@link RxAction}，异常发送{@link RxRetry}。
     * 开始结束发送{@link RxLoading}。
     */
    protected <T> void postHttpRetryAndLoadingAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, true, true);
    }

    /**
     * 异步执行重试操作，发送{@link RxAction}
     */
    public void postRetryAction(@NonNull RxRetry rxRetry) {
        RxAction rxAction = newRxAction(rxRetry.getTag());
        if (hasRxAction(rxAction)) {
            return;
        }
        postHttpRetryAction(rxAction, rxRetry.getObservable());
    }

    /**
     * 同步发送{@link RxAction}
     */
    public void postLocalAction(@NonNull String actionId, Object... data) {
        RxAction rxAction = newRxAction(actionId, data);
        postRxAction(rxAction);
        removeRxAction(rxAction);
    }

    /**
     * 同步发送{@link RxChange}
     */
    public void postLocalChange(@NonNull String tag) {
        RxChange rxChange = RxChange.newInstance(tag);
        postRxChange(rxChange);
    }
}
