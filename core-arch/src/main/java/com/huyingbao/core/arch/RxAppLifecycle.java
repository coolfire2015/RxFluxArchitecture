package com.huyingbao.core.arch;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 * 跟随App生命周期
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxAppLifecycle implements LifecycleObserver {
//    @Inject
//    volatile DispatchingAndroidInjector<Object> androidInjector;

    protected Application mApplication;

    public RxAppLifecycle(Application application) {
        mApplication = application;
//        injectIfNecessary();
    }

//    /**
//     * Implementations should return an {@link AndroidInjector} for the concrete {@link
//     * RxAppLifecycle}. Typically, that injector is a {@link dagger.Component}.
//     */
//    protected abstract AndroidInjector<? extends RxAppLifecycle> rxAppLifecycleInjector();

//    /**
//     * Lazily injects the {@link RxAppLifecycle}'s members. Injection cannot be performed in {@link
//     * Application#onCreate()} since {@link android.content.ContentProvider}s' {@link
//     * android.content.ContentProvider#onCreate() onCreate()} method will be called first and might
//     * need injected members on the application. Injection is not performed in the constructor, as
//     * that may result in members-injection methods being called before the constructor has completed,
//     * allowing for a partially-constructed instance to escape.
//     */
//    private void injectIfNecessary() {
//        if (androidInjector == null) {
//            synchronized (this) {
//                if (androidInjector == null) {
//                    @SuppressWarnings("unchecked")
//                    AndroidInjector<RxAppLifecycle> rxAppLifecycleInjector = (AndroidInjector<RxAppLifecycle>) rxAppLifecycleInjector();
//                    rxAppLifecycleInjector.inject(this);
//                    if (androidInjector == null) {
//                        throw new IllegalStateException(
//                                "The AndroidInjector returned from applicationInjector() did not inject the "
//                                        + "RxAppLifecycle");
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public AndroidInjector<Object> androidInjector() {
//        // injectIfNecessary should already be called unless we are about to inject a ContentProvider,
//        // which can happen before Application.onCreate()
//        injectIfNecessary();
//        return androidInjector;
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public abstract void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public abstract void onLowMemory();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public abstract void onTerminate();
}
