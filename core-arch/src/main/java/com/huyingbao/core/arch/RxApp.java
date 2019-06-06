package com.huyingbao.core.arch;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import dagger.android.DaggerApplication;

import static com.huyingbao.core.arch.RxFlux.TAG;

/**
 * Application实现Dagger.Android依赖注入。
 * <p>
 * 通过反射获取RxAppLifecycleOwner，并实现生命周期状态分发到{@link RxAppLifecycle}。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxApp extends DaggerApplication {
    @Inject
    RxFlux mRxFlux;

    private LifecycleRegistry mLifecycleRegistry;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LifecycleOwner lifecycleOwner = getAnnotationGeneratedRxAppLifecycleOwner();
        if (lifecycleOwner != null) {
            mLifecycleRegistry = (LifecycleRegistry) lifecycleOwner.getLifecycle();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //application创建的时候调用该方法，使RxFlux可以接受Activity生命周期回调
        registerActivityLifecycleCallbacks(mRxFlux);
        if (mLifecycleRegistry != null) {
            mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mLifecycleRegistry != null) {
            mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mLifecycleRegistry != null) {
            mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        }
    }

    @Nullable
    @SuppressWarnings({"unchecked", "deprecation", "TryWithIdenticalCatches"})
    private LifecycleOwner getAnnotationGeneratedRxAppLifecycleOwner() {
        LifecycleOwner result = null;
        try {
            Class<LifecycleOwner> clazz = (Class<LifecycleOwner>) Class.forName("com.huyingbao.core.arch.RxAppLifecycleOwner");
            result = clazz.getConstructor(Application.class).newInstance(this);
        } catch (ClassNotFoundException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Failed to find RxAppLifecycleOwner. You should include an"
                        + " annotationProcessor compile dependency on com.github.coolfire2015.RxFluxArchitecture:core-arch-processor"
                        + " in your application and a @RxAppObserver annotated RxAppLifecycle subclass"
                        + " and a @RxAppOwner annotated RxApp implementation");
            }
            // These exceptions can't be squashed across all versions of Android.
        } catch (InstantiationException e) {
            throwIncorrectRxAppLifecycle(e);
        } catch (IllegalAccessException e) {
            throwIncorrectRxAppLifecycle(e);
        } catch (NoSuchMethodException e) {
            throwIncorrectRxAppLifecycle(e);
        } catch (InvocationTargetException e) {
            throwIncorrectRxAppLifecycle(e);
        }
        return result;
    }

    private void throwIncorrectRxAppLifecycle(Exception e) {
        throw new IllegalStateException("RxAppLifecycleOwner is implemented incorrectly."
                + " If you've manually implemented this class, remove your implementation. The Annotation"
                + " processor will generate a correct implementation.", e);
    }
}
