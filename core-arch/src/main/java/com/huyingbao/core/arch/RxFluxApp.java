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
public abstract class RxFluxApp extends DaggerApplication {
    @Inject
    RxFlux mRxFlux;

    private RxAppLifecycle mGlobalRxAppLifecycle;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mGlobalRxAppLifecycle = getAnnotationGeneratedGlideModules();
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
    private RxAppLifecycle getAnnotationGeneratedGlideModules() {
        RxAppLifecycle result = null;
        try {
            Class<RxAppLifecycle> clazz = (Class<RxAppLifecycle>)
                    Class.forName("com.huyingbao.core.arch.RxAppLifecycleImpl");
            result = clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Failed to find GeneratedAppGlideModule. You should include an"
                        + " annotationProcessor compile dependency on com.github.bumptech.glide:compiler"
                        + " in your application and a @GlideModule annotated AppGlideModule implementation or"
                        + " LibraryGlideModules will be silently ignored");
            }
            // These exceptions can't be squashed across all versions of Android.
        } catch (InstantiationException e) {
            throwIncorrectGlideModule(e);
        } catch (IllegalAccessException e) {
            throwIncorrectGlideModule(e);
        } catch (NoSuchMethodException e) {
            throwIncorrectGlideModule(e);
        } catch (InvocationTargetException e) {
            throwIncorrectGlideModule(e);
        }
        return result;
    }

    private void throwIncorrectGlideModule(Exception e) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly."
                + " If you've manually implemented this class, remove your implementation. The Annotation"
                + " processor will generate a correct implementation.", e);
    }
}
