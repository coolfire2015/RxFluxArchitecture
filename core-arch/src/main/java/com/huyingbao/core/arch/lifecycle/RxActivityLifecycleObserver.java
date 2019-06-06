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
 * Activity生命周期观察者，将Activity持有的{@link com.huyingbao.core.arch.store.RxStore}关联其生命周期。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxActivityLifecycleObserver implements LifecycleObserver {
    private final Activity mActivity;

    public RxActivityLifecycleObserver(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 在onCreate(Bundle)完成依赖注入之后调用
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
