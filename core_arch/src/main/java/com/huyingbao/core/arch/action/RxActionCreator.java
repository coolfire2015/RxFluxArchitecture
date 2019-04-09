package com.huyingbao.core.arch.action;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * 这个类必须被继承,提供一个可以创建RxAction的方法.
 * 按钮被点击触发回调方法，在回调方法中调用ActionCreator提供的有语义的的方法，
 * ActionCreator会根据传入参数创建Action并通过Dispatcher发送给Store，
 * 所有订阅了这个Action的Store会接收到订阅的Action并消化Action，
 * 然后Store会发送UI状态改变的事件给相关的Activity（或Fragment)，
 * Activity在收到状态发生改变的事件之后，开始更新UI（更新UI的过程中会从Store获取所有需要的数据）。
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
        if (mRxActionManager.contains(rxAction)) {
            return;
        }
        mRxActionManager.add(rxAction, httpObservable
                //指定IO线程
                .subscribeOn(Schedulers.io())
                // 调用开始
                .doOnSubscribe(subscription -> {
                    if (canShowLoading) {
                        //发送RxLoading(显示)事件
                        mRxDispatcher.postRxLoading(RxLoading.newInstance(rxAction, true));
                    }
                })
                // 调用结束
                .doAfterTerminate(() -> {
                    if (canShowLoading) {
                        //发送RxLoading(消失)事件
                        mRxDispatcher.postRxLoading(RxLoading.newInstance(rxAction, false));
                    }
                })
                // 操作结束,在io线程中接收接收反馈,没有切换线程
                .subscribe(
                        //操作进行中
                        response -> {
                            rxAction.setResponse(response);
                            //发送RxAction事件
                            mRxDispatcher.postRxAction(rxAction);
                        },
                        //操作异常
                        throwable -> {
                            if (canRetry) {
                                //发送RxRetry事件
                                mRxDispatcher.postRxRetry(RxRetry.newInstance(rxAction, throwable, httpObservable));
                            } else {
                                //发送RxError事件
                                mRxDispatcher.postRxError(RxError.newInstance(rxAction, throwable));
                            }
                            mRxActionManager.remove(rxAction);
                        },
                        //操作结束
                        () -> {
                            mRxActionManager.remove(rxAction);
                        }
                ));
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
        mRxDispatcher.postRxAction(rxAction);
        mRxActionManager.remove(rxAction);
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
        if (mRxActionManager.contains(rxAction)) {
            return;
        }
        postHttpRetryAction(rxAction, rxRetry.getObservable());
    }
}
