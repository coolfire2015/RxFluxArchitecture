package com.huyingbao.core.lifecycle;

import android.app.Activity;

import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.view.RxFluxView;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by liujunfeng on 2017/12/13.
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
        if (mActivity instanceof RxFluxView) {
            List<RxStore> rxStoreList = ((RxFluxView) mActivity).getLifecycleRxStoreList();
            if (rxStoreList != null && rxStoreList.size() > 0)
                for (RxStore rxStore : rxStoreList)
                    ((FragmentActivity) mActivity).getLifecycle().addObserver(rxStore);
        }
    }
}
