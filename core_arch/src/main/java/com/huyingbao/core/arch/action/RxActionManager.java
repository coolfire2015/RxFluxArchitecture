package com.huyingbao.core.arch.action;

import com.huyingbao.core.arch.model.RxAction;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.collection.ArrayMap;
import androidx.core.util.Pair;
import io.reactivex.disposables.Disposable;


/**
 * 订阅管理类
 * 将rxAction与disposable连接起来，
 * action的tag作为key
 *
 * @author liujunfeng
 * @date 2019/1/1
 */
@Singleton
public class RxActionManager {
    /**
     * 管理订阅的ArrayMap
     * rxAction的tag作为key
     */
    private ArrayMap<String, Pair<Integer, Disposable>> mMap;

    @Inject
    RxActionManager() {
        mMap = new ArrayMap<>();
    }

    /**
     * 添加订阅管理,将RxAction和Disposable添加到RxActionManager
     * <p>
     * 成对添加
     * <p>
     * 如果已经有了一个对应RxAction的订阅,则取消订阅
     *
     * @param rxAction
     * @param disposable
     */
    public void add(RxAction rxAction, Disposable disposable) {
        Pair<Integer, Disposable> old = mMap.put(rxAction.getTag(), getPair(rxAction, disposable));
        if (old != null && old.second != null && !old.second.isDisposed()) {
            old.second.dispose();
        }
    }

    /**
     * 从管理器中取消订阅,移除该rxAction，停止该rxAction对应的操作
     *
     * @param rxAction
     */
    public void remove(RxAction rxAction) {
        Pair<Integer, Disposable> old = mMap.remove(rxAction.getTag());
        if (old != null && old.second != null && !old.second.isDisposed()) {
            old.second.dispose();
        }
    }

    /**
     * 检查
     * <p>
     * 1:订阅管理器是否已经有了该rxAction
     * <p>
     * 2:rxAction是否已经运行一个disposable
     *
     * @param rxAction 获取rxAction的tag，tag的hashcode对应一个disposable
     * @return
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
     * 清除所有的disposables
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
     * 创建一个新的pair
     *
     * @param rxAction   转变成hashcode
     * @param disposable
     * @return
     */
    private Pair<Integer, Disposable> getPair(RxAction rxAction, Disposable disposable) {
        return new Pair<>(rxAction.hashCode(), disposable);
    }
}
