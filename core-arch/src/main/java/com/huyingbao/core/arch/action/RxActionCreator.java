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
 * 这个类必须被继承,提供一个可以创建RxAction的方法.
 * <p>
 * 按钮被点击触发回调方法，在回调方法中调用ActionCreator提供的有语义的的方法，
 * ActionCreator会根据传入参数创建Action并通过Dispatcher发送给Store，
 * <p>
 * 所有订阅了这个Action的Store会接收到订阅的Action并消化Action，
 * 然后Store会发送UI状态改变的事件RxChange给相关的View(Activity或Fragment)
 * <p>
 * View在收到状态发生改变的事件之后，开始更新UI（更新UI的过程中会从Store获取所有需要的数据）。
 * <p>
 * 类似MVP模式中的Presenter,只是方法执行完成没有回调方法
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
     * 发送网络action，通知View操作进度更新
     *
     * @param rxAction
     * @param httpObservable
     * @param canShowLoading true:有进度显示,false:无进度显示
     * @param canRetry       true:操作异常可重试,false:操作异常抛异常
     * @param <T>
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
     * 订阅管理器是否已经有了该action
     *
     * @param rxAction
     * @return
     */
    protected boolean hasRxAction(RxAction rxAction) {
        return mRxActionManager.contains(rxAction);
    }

    /**
     * 主要是为了和RxJava整合,用在调用网络接口api获取数据之后,被观察者得到数据,发生订阅关系,将返回的数据
     * 或者error封装成action,postAction或者postError出去
     * 订阅管理,将RxAction和Disposable添加到DisposableManager
     *
     * @param rxAction
     * @param disposable
     */
    protected void addRxAction(RxAction rxAction, Disposable disposable) {
        mRxActionManager.add(rxAction, disposable);
    }

    /**
     * 订阅管理器,移除该action，停止该action对应的操作
     *
     * @param rxAction
     */
    protected void removeRxAction(RxAction rxAction) {
        mRxActionManager.remove(rxAction);
    }

    /**
     * 通过调度器dispatcher将action推出去
     *
     * @param rxAction
     */
    protected void postRxAction(RxAction rxAction) {
        mRxDispatcher.postRxAction(rxAction);
    }

    /**
     * 通过调度器dispatcher将action对应的RxLoading事件推出去
     *
     * @param tag
     * @param isLoading true:显示，false:消失
     */
    protected void postRxLoading(String tag, boolean isLoading) {
        mRxDispatcher.postRxLoading(RxLoading.newInstance(tag, isLoading));
    }

    /**
     * 通过调度器dispatcher将action对应的RxRetry事件推出去
     *
     * @param tag
     * @param httpObservable
     * @param throwable
     * @param <T>
     */
    protected <T> void postRxRetry(String tag, Observable<T> httpObservable, Throwable throwable) {
        mRxDispatcher.postRxRetry(RxRetry.newInstance(tag, throwable, httpObservable));
    }

    /**
     * 通过调度器dispatcher将action对应的RxError事件推出去
     *
     * @param tag
     * @param throwable
     */
    protected void postRxError(String tag, Throwable throwable) {
        mRxDispatcher.postRxError(RxError.newInstance(tag, throwable));
    }

    /**
     * 通过调度器dispatcher将rxChange事件推出去
     *
     * @param rxChange
     */
    protected void postRxChange(RxChange rxChange) {
        mRxDispatcher.postRxChange(rxChange);
    }


    /**
     * 创建新的RxAction
     *
     * @param tag  rxAction tag对应具体是什么样的方法
     * @param data 键值对型的参数pair-value parameters(Key - Object))
     * @return
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
     * 发送网络action
     *
     * @param rxAction
     * @param httpObservable
     */
    protected <T> void postHttpAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, false, false);
    }

    /**
     * 发送网络action
     * <p>
     * 1:通知View操作进度更新
     *
     * @param rxAction
     * @param httpObservable
     */
    protected <T> void postHttpLoadingAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, true, false);
    }

    /**
     * 发送网络action，
     * <p>
     * 1:如果有异常，可以重试
     *
     * @param rxAction
     * @param httpObservable
     */
    protected <T> void postHttpRetryAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, false, true);
    }

    /**
     * 发送网络action，
     * <p>
     * 1:通知View操作进度更新
     * <p>
     * 2:如果有异常，可以重试
     *
     * @param rxAction
     * @param httpObservable
     */
    protected <T> void postHttpRetryAndLoadingAction(RxAction rxAction, Observable<T> httpObservable) {
        postRxAction(rxAction, httpObservable, true, true);
    }

    /**
     * 发送本地action
     *
     * @param actionId
     * @param data
     */
    public void postLocalAction(@NonNull String actionId, Object... data) {
        RxAction rxAction = newRxAction(actionId, data);
        postRxAction(rxAction);
        removeRxAction(rxAction);
    }

    /**
     * 发送本地change,由view直接处理
     *
     * @param tag
     */
    public void postLocalChange(@NonNull String tag) {
        RxChange rxChange = RxChange.newInstance(tag);
        postRxChange(rxChange);
    }

    /**
     * 接收到重试action之后,进行重试操作
     * <p>
     * 发送重试action
     *
     * @param rxRetry
     */
    public void postRetryAction(@NonNull RxRetry rxRetry) {
        RxAction rxAction = newRxAction(rxRetry.getTag());
        if (hasRxAction(rxAction)) {
            return;
        }
        postHttpRetryAction(rxAction, rxRetry.getObservable());
    }
}
