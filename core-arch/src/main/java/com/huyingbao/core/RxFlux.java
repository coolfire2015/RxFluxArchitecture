package com.huyingbao.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.huyingbao.core.dispatcher.Dispatcher;
import com.huyingbao.core.dispatcher.DisposableManager;
import com.huyingbao.core.dispatcher.RxViewDispatch;
import com.huyingbao.core.store.RxStore;

import java.util.List;
import java.util.Stack;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 主类
 * 必须在application创建的时候调用该类的实例方法, 并仅调用一次.
 * 这个类会自动跟踪应用程序的生命周期,
 * 并且注销每个activity剩余的订阅subscriptions
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RxFlux extends FragmentManager.FragmentLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Inject
    Dispatcher mDispatcher;
    @Inject
    DisposableManager mDisposableManager;

    private int mActivityCounter;
    private Stack<Activity> mActivityStack;

    @Inject
    public RxFlux() {
        mActivityCounter = 0;
        mActivityStack = new Stack<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        mActivityCounter++;
        mActivityStack.add(activity);
        if (activity instanceof RxViewDispatch && activity instanceof FragmentActivity) {
            List<RxStore> rxStoreList = ((RxViewDispatch) activity).getLifecycleRxStoreList();
            if (rxStoreList != null && rxStoreList.size() > 0)
                for (RxStore rxStore : rxStoreList)
                    ((FragmentActivity) activity).getLifecycle().addObserver(rxStore);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof RxViewDispatch)
            mDispatcher.subscribeRxView((RxViewDispatch) activity);
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(this, true);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof RxViewDispatch)
            mDispatcher.unsubscribeRxView((RxViewDispatch) activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityCounter--;
        mActivityStack.remove(activity);
        if (mActivityCounter == 0 || mActivityStack.size() == 0)
            shutdown();
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        if (f instanceof RxViewDispatch) {
            List<RxStore> rxStoreList = ((RxViewDispatch) f).getLifecycleRxStoreList();
            if (rxStoreList != null && rxStoreList.size() > 0)
                for (RxStore rxStore : rxStoreList)
                    f.getLifecycle().addObserver(rxStore);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        if (f instanceof RxViewDispatch)
            mDispatcher.subscribeRxView((RxViewDispatch) f);
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        if (f instanceof RxViewDispatch)
            mDispatcher.unsubscribeRxView((RxViewDispatch) f);
    }

    public void finishAllActivity() {
        while (!mActivityStack.empty())
            mActivityStack.pop().finish();
    }

    public void finishActivity(Class<?> cls) {
        finishActivity(getActivity(cls));
    }

    private void shutdown() {
        mDisposableManager.clear();
        mDispatcher.unsubscribeAll();
    }

    private void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    private Activity getActivity(Class<?> cls) {
        for (Activity activity : mActivityStack)
            if (activity.getClass().equals(cls))
                return activity;
        return null;
    }
}
