package com.huyingbao.core.arch.lifecycle;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.view.RxFluxView;

/**
 * Activity生命周期订阅者对象,
 * <p>
 * 在Activity对象外,执行与该Activity生命周期相关联的方法.
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxActivityLifecycleObserver implements LifecycleObserver {
    private final Activity mActivity;

    public RxActivityLifecycleObserver(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * activity创建成功之后调用,
     * <p>
     * 若activity是{@link RxFluxView}的子类, 获取需要关联的rxStore
     * <p>
     * 使用LifecycleObserver,接收ON_CREATE,确保在RxFluxActivity onCreate()方法之后执行,
     * 在RxFluxActivity onCreate()方法实现依赖注入
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        if (mActivity instanceof RxFluxView) {
            ViewModel rxStore = ((RxFluxView) mActivity).getRxStore();
            if (rxStore instanceof RxActivityStore) {
                //rxStore关联activity生命周期
                ((FragmentActivity) mActivity).getLifecycle().addObserver((RxActivityStore) rxStore);
            }
        }
    }
}
