package com.huyingbao.core.arch.lifecycle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.huyingbao.core.arch.store.RxFragmentStore;
import com.huyingbao.core.arch.view.RxFluxView;

/**
 * Fragment生命周期观察者，将Fragment持有的{@link com.huyingbao.core.arch.store.RxStore}关联其生命周期。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxFragmentLifecycleObserver implements LifecycleObserver {
    private final Fragment mFragment;

    public RxFragmentLifecycleObserver(Fragment fragment) {
        this.mFragment = fragment;
    }

    /**
     * 在onAttach()完成依赖注入之后调用
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
