package com.huyingbao.core.arch;


import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import dagger.android.support.DaggerApplication;

import static com.huyingbao.core.arch.RxFlux.TAG;

/**
 * Application实现相应的接口
 * HasActivityInjector、
 * HasFragmentInjector、
 * HasSupportFragmentInjector、
 * HasServiceInjector、
 * HasBroadcastReceiverInjector
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxApp extends DaggerApplication {
    @Inject
    RxFlux mRxFlux;

    private RxAppLifecycle mGlobalRxAppLifecycle;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mGlobalRxAppLifecycle = getAnnotationGeneratedRxAppLifecycleImpl();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //application创建的时候调用该方法，
        //使RxFlux可以接受Activity生命周期回调
        registerActivityLifecycleCallbacks(mRxFlux);
        if (mGlobalRxAppLifecycle != null) {
            mGlobalRxAppLifecycle.onCreate(this);
        }
    }

    @Nullable
    @SuppressWarnings({"unchecked", "deprecation", "TryWithIdenticalCatches"})
    private RxAppLifecycle getAnnotationGeneratedRxAppLifecycleImpl() {
        RxAppLifecycle result = null;
        try {
            Class<RxAppLifecycle> clazz = (Class<RxAppLifecycle>)
                    Class.forName("com.huyingbao.core.arch.RxAppLifecycleImpl");
            result = clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Failed to find RxAppLifecycleImpl. You should include an"
                        + " annotationProcessor compile dependency on com.github.coolfire2015.RxFluxArchitecture:core-arch-processor"
                        + " in your application and a @RxAppDelegate annotated RxAppLifecycle implementation"
                        + " and a @RxAppBody annotated RxApp implementation");
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
        throw new IllegalStateException("RxAppLifecycleImpl is implemented incorrectly."
                + " If you've manually implemented this class, remove your implementation. The Annotation"
                + " processor will generate a correct implementation.", e);
    }
}
