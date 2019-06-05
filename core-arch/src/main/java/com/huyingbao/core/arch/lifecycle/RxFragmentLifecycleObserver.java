package com.huyingbao.core.arch.lifecycle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.huyingbao.core.arch.store.RxFragmentStore;
import com.huyingbao.core.arch.view.RxFluxView;

/**
 * Fragment生命周期订阅者对象,
 * <p>
 * 在Fragment对象外,执行与该Fragment生命周期相关联的方法.
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxFragmentLifecycleObserver implements LifecycleObserver {
    private final Fragment mFragment;

    public RxFragmentLifecycleObserver(Fragment fragment) {
        this.mFragment = fragment;
    }

    /**
     * fragment创建成功之后调用,
     * <p>
     * 若fragment是{@link RxFluxView}的子类, 获取需要关联的rxStore
     * <p>
     * 使用LifecycleObserver,接收ON_CREATE,确保在RxFluxFragment onAttach()方法之后执行,
     * 在RxFluxFragment onAttach()方法实现依赖注入
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        if (mFragment instanceof RxFluxView) {
            ViewModel rxStore = ((RxFluxView) mFragment).getRxStore();
            if (rxStore instanceof RxFragmentStore) {
                //rxStore关联Fragment生命周期
                mFragment.getLifecycle().addObserver((RxFragmentStore) rxStore);
            }
        }
    }
}
