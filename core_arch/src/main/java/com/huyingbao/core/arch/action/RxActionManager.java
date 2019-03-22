package com.huyingbao.core.arch.action;

import com.huyingbao.core.arch.model.RxAction;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.collection.ArrayMap;
import androidx.core.util.Pair;
import io.reactivex.disposables.Disposable;


/**
 * 订阅管理类
 * 将action与action处理事件连接起来，
 * action的tag作为key
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public final class RxActionManager {
    /**
     * 管理订阅的ArrayMap
     * action的tag作为key
     */
    private ArrayMap<String, Pair<Integer, Disposable>> mMap;

    @Inject
    RxActionManager() {
        mMap = new ArrayMap<>();
    }

    /**
     * 添加一个action和disposable,如果已经有了一个对应action 的订阅,则取消订阅
     */
    void add(RxAction action, Disposable disposable) {
        Pair<Integer, Disposable> old = mMap.put(action.getTag(), getPair(action, disposable));
        if (old != null && old.second != null && !old.second.isDisposed())
            old.second.dispose();
    }

    /**
     * 从管理器中取消订阅
     */
    void remove(RxAction action) {
        Pair<Integer, Disposable> old = mMap.remove(action.getTag());
        if (old != null && old.second != null && !old.second.isDisposed())
            old.second.dispose();
    }

    /**
     * 检查action是否已经运行一个disposable
     *
     * @param action 获取action的tag，tag的hashcode对应一个disposable
     * @return
     */
    boolean contains(RxAction action) {
        Pair<Integer, Disposable> old = mMap.get(action.getTag());
        return old != null
                && old.first != null
                && old.first == action.hashCode()
                && old.second != null
                && !old.second.isDisposed();
    }

    /**
     * 清除所有的disposables
     */
    public synchronized void clear() {
        if (mMap.isEmpty()) return;
        for (Pair<Integer, Disposable> pair : mMap.values())
            if (pair.second != null && !pair.second.isDisposed())
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
