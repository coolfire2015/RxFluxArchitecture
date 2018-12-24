package com.huyingbao.core.arch.lifecycle;

import android.util.Log;

import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.view.RxFluxView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by liujunfeng on 2017/12/13.
 */
public class RxFragmentLifecycleObserver implements LifecycleObserver {
    private final Fragment mFragment;

    public RxFragmentLifecycleObserver(Fragment fragment) {
        this.mFragment = fragment;
    }

    /**
     * fragment创建成功之后调用,
     * 若fragment是RxViewDispatch的子类,
     * 获取需要关联的RxStoreList
     * 将RxStoreList同fragment生命周期关联
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.e("RxFlux","onCreate-fragment");
        if (mFragment instanceof RxFluxView) {
            RxStore rxStore = ((RxFluxView) mFragment).getRxStore();
            if (rxStore != null){
                mFragment.getLifecycle().addObserver(rxStore);
            }
        }
    }
}
