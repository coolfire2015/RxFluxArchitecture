package com.huyingbao.core.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;

import com.huyingbao.core.dispatcher.RxViewDispatch;
import com.huyingbao.core.store.RxStore;

import java.util.List;

/**
 * Created by liujunfeng on 2017/12/13.
 */
public class FragmentLifecycleObserver implements LifecycleObserver {
    private final Fragment mFragment;

    public FragmentLifecycleObserver(Fragment fragment) {
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
        if (mFragment instanceof RxViewDispatch) {
            List<RxStore> rxStoreList = ((RxViewDispatch) mFragment).getLifecycleRxStoreList();
            if (rxStoreList != null && rxStoreList.size() > 0)
                for (RxStore rxStore : rxStoreList)
                    mFragment.getLifecycle().addObserver(rxStore);
        }
    }
}
