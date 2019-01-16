package com.huyingbao.core.arch.lifecycle;

import android.app.Activity;
import android.util.Log;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.view.RxFluxView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

/**
 * Created by liujunfeng on 2018/12/13.
 */
public class RxActivityLifecycleObserver implements LifecycleObserver {
    private final Activity mActivity;

    public RxActivityLifecycleObserver(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * activity创建成功之后调用,
     * 若activity是RxViewDispatch的子类,
     * 获取需要关联的RxStoreList
     * 将RxStoreList同activity生命周期关联
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.v("RxFlux", "1.2-onCreateLifecycle ");
        if (mActivity instanceof RxFluxView) {
            ViewModel rxStore = ((RxFluxView) mActivity).getRxStore();
            if (rxStore instanceof RxActivityStore) {
                ((FragmentActivity) mActivity).getLifecycle().addObserver((RxActivityStore)rxStore);
            }
        }
    }
}
