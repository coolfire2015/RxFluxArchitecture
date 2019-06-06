package com.huyingbao.core.arch.action;

import androidx.collection.ArrayMap;
import androidx.core.util.Pair;

import com.huyingbao.core.arch.model.RxAction;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;


/**
 * 订阅管理类：关联并管理{@link RxAction}与{@link Disposable}
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class RxActionManager {
    /**
     * 管理订阅的ArrayMap，{@link RxAction}的tag作为key
     */
    private ArrayMap<String, Pair<Integer, Disposable>> mMap;

    @Inject
    public RxActionManager() {
        mMap = new ArrayMap<>();
    }

    /**
     * 成对添加{@link RxAction}和{@link Disposable}到管理订阅的ArrayMap中。
     * 如果已存在，则取消{@link Disposable}中观察者与被观察者订阅关系，
     * 停止被观察者{@link io.reactivex.Observable}方法。
     */
    public void add(RxAction rxAction, Disposable disposable) {
        Pair<Integer, Disposable> old = mMap.put(rxAction.getTag(), getPair(rxAction, disposable));
        if (old != null && old.second != null && !old.second.isDisposed()) {
            old.second.dispose();
        }
    }

    /**
     * 移除{@link RxAction}，取消{@link Disposable}中观察者与被观察者订阅关系，
     * 停止被观察者{@link io.reactivex.Observable}方法。
     */
    public void remove(RxAction rxAction) {
        Pair<Integer, Disposable> old = mMap.remove(rxAction.getTag());
        if (old != null && old.second != null && !old.second.isDisposed()) {
            old.second.dispose();
        }
    }

    /**
     * 检查否已存在{@link RxAction}
     */
    public boolean contains(RxAction rxAction) {
        Pair<Integer, Disposable> old = mMap.get(rxAction.getTag());
        return old != null
                && old.first != null
                && old.first == rxAction.hashCode()
                && old.second != null
                && !old.second.isDisposed();
    }

    /**
     * 清除所有的{@link Disposable}
     */
    public synchronized void clear() {
        if (mMap.isEmpty()) {
            return;
        }
        for (Pair<Integer, Disposable> pair : mMap.values()) {
            if (pair.second != null && !pair.second.isDisposed()) {
                pair.second.dispose();
            }
        }
    }

    /**
     * 创建一个新的{@link Pair}
     *
     * @param rxAction hashcode作为Key
     */
    private Pair<Integer, Disposable> getPair(RxAction rxAction, Disposable disposable) {
        return new Pair<>(rxAction.hashCode(), disposable);
    }
}
