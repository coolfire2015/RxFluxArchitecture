package com.huyingbao.core.arch.action;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 这个类必须被继承,提供一个可以创建RxAction的方法.
 * 按钮被点击触发回调方法，在回调方法中调用ActionCreator提供的有语义的的方法，
 * ActionCreator会根据传入参数创建Action并通过Dispatcher发送给Store，
 * 所有订阅了这个Action的Store会接收到订阅的Action并消化Action，
 * 然后Store会发送UI状态改变的事件给相关的Activity（或Fragment)，
 * Activity在收到状态发生改变的事件之后，开始更新UI（更新UI的过程中会从Store获取所有需要的数据）。
 *
 * @author liujunfeng
 * @date 2019/1/1
 */
public abstract class RxActionCreator {
    private final RxDispatcher mRxDispatcher;
    private final RxActionManager mRxActionManager;

    public RxActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        this.mRxDispatcher = rxDispatcher;
        this.mRxActionManager = rxActionManager;
    }

    /**
     * 创建新的RxAction
     *
     * @param tag  action tag对应具体是什么样的方法
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
     * 订阅管理器是否已经有了该action
     *
     * @param rxAction
     * @return
     */
    private boolean hasRxAction(RxAction rxAction) {
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
    private void addRxAction(RxAction rxAction, Disposable disposable) {
        mRxActionManager.add(rxAction, disposable);
    }

    /**
     * 订阅管理器,移除该action，停止该action对应的操作
     *
     * @param rxAction
     */
    private void removeRxAction(RxAction rxAction) {
        mRxActionManager.remove(rxAction);
    }

    /**
     * 通过调度器dispatcher将action事件推出去
     *
     * @param rxAction
     * @param response
     * @param <T>
     */
    private <T> void postRxAction(@NonNull RxAction rxAction, T response) {
        rxAction.setResponse(response);
        mRxDispatcher.postRxAction(rxAction);
        //发送通知，移除Action
        removeRxAction(rxAction);
    }

    /**
     * 通过调度器dispatcher将action对应的RxError事件推出去
     *
     * @param action
     * @param throwable
     */
    private void postRxError(@NonNull RxAction action, Throwable throwable) {
        mRxDispatcher.postRxError(RxError.newInstance(action, throwable));
        //发送通知，移除Action
        removeRxAction(action);
    }

    /**
     * 通过调度器dispatcher将action对应的RxRetry事件推出去
     *
     * @param action
     * @param throwable
     * @param httpObservable
     */
    private <T> void postRxRetry(@NonNull RxAction action, Throwable throwable, Observable<T> httpObservable) {
        mRxDispatcher.postRxRetry(RxRetry.newInstance(action, throwable, httpObservable));
        //发送通知，移除Action
        removeRxAction(action);
    }


    /**
     * 通过调度器dispatcher将action对应的RxLoading事件推出去
     *
     * @param rxAction
     * @param isLoading true:显示，false:消失
     */
    private void postRxLoading(RxAction rxAction, boolean isLoading) {
        mRxDispatcher.postRxLoading(RxLoading.newInstance(rxAction, isLoading));
    }

    /**
     * 发送网络action
     *
     * @param rxAction
     * @param httpObservable
     */
    protected <T> void postHttpAction(RxAction rxAction, Observable<T> httpObservable) {
        if (hasRxAction(rxAction)) {
            return;
        }
        addRxAction(rxAction, httpObservable
                // 1:指定IO线程
                .subscribeOn(Schedulers.io())
                // 2:指定主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 2:指定主线程
                .subscribe(
                        response -> postRxAction(rxAction, response),
                        throwable -> postRxError(rxAction, throwable)
                ));
    }

    /**
     * 发送网络action，通知View操作进度更新
     *
     * @param rxAction
     * @param httpObservable
     */
    protected <T> void postHttpLoadingAction(RxAction rxAction, Observable<T> httpObservable) {
        if (hasRxAction(rxAction)) {
            return;
        }
        addRxAction(rxAction, httpObservable
                // 1:指定IO线程
                .subscribeOn(Schedulers.io())
                // 调用开始
                .doOnSubscribe(subscription -> postRxLoading(rxAction, true))
                // 调用结束
                .doAfterTerminate(() -> postRxLoading(rxAction, false))
                // 2:指定主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 2:指定主线程
                .subscribe(
                        response -> postRxAction(rxAction, response),
                        throwable -> postRxError(rxAction, throwable)
                ));
    }

    /**
     * 发送网络action，如果有异常，可以重试
     *
     * @param rxAction
     * @param httpObservable
     */
    protected <T> void postHttpRetryAction(RxAction rxAction, Observable<T> httpObservable) {
        if (hasRxAction(rxAction)) {
            return;
        }
        addRxAction(rxAction, httpObservable
                // 1:指定IO线程
                .subscribeOn(Schedulers.io())
                // 2:指定主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 2:指定主线程
                .subscribe(
                        response -> postRxAction(rxAction, response),
                        throwable -> postRxRetry(rxAction, throwable, httpObservable)
                ));

    }

    /**
     * 发送本地action
     *
     * @param actionId
     * @param data
     */
    public void postLocalAction(@NonNull String actionId, Object... data) {
        RxAction action = newRxAction(actionId, data);
        mRxDispatcher.postRxAction(action);
        //发送通知，移除Action
        removeRxAction(action);
    }

    /**
     * 接收到重试action之后,进行重试操作
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
