package com.huyingbao.core.dispatcher;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;

import com.huyingbao.core.action.RxAction;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;


/**
 * 订阅管理类
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public final class DisposableManager {
    /**
     * 管理订阅的ArrayMap
     */
    private ArrayMap<String, Pair<Integer, Disposable>> mMap;

    @Inject
    public DisposableManager() {
        mMap = new ArrayMap<>();
    }

    /**
     * 添加一个action和disposable,如果已经有了一个对应action 的订阅,则取消订阅
     */
    public void add(RxAction action, Disposable disposable) {
        Pair<Integer, Disposable> old = mMap.put(action.getType(), getPair(action, disposable));
        if (old != null && !old.second.isDisposed()) old.second.dispose();
    }

    /**
     * 从管理器中取消订阅
     */
    public void remove(RxAction action) {
        Pair<Integer, Disposable> old = mMap.remove(action.getType());
        if (old != null && !old.second.isDisposed()) old.second.dispose();
    }

    /**
     * 检查action是否已经运行一个disposable
     */
    public boolean contains(RxAction action) {
        Pair<Integer, Disposable> old = mMap.get(action.getType());
        return old != null && old.first == action.hashCode() && !old.second.isDisposed();
    }

    /**
     * 清除所有的disposables
     */
    public synchronized void clear() {
        if (mMap.isEmpty()) return;
        for (Pair<Integer, Disposable> pair : mMap.values())
            if (!pair.second.isDisposed())
                pair.second.dispose();
    }

    /**
     * 创建一个新的pair
     *
     * @param action     转变成hashcode
     * @param disposable
     * @return
     */
    private Pair<Integer, Disposable> getPair(RxAction action, Disposable disposable) {
        return new Pair<>(action.hashCode(), disposable);
    }
}
