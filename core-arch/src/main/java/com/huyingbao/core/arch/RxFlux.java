package com.huyingbao.core.arch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver;
import com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver;
import com.huyingbao.core.arch.view.RxSubscriberView;

import java.util.Stack;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 主类
 * 必须在application创建的时候调用该类的实例方法, 并仅调用一次.
 * 这个类会自动跟踪应用程序的生命周期,
 * 并且注销每个activity剩余的订阅subscriptions
 * <p>
 * 抽象的类并不能实例化
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class RxFlux extends FragmentManager.FragmentLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    /**
     * Inject 用来标记需要注入的依赖
     * 被标注的属性不能使用private修饰，否则无法注入
     *
     * @param rxBus
     */
    @Inject
    RxDispatcher mRxDispatcher;
    @Inject
    RxActionManager mRxActionManager;

    private int mActivityCounter;
    private Stack<Activity> mActivityStack;

    /**
     * Inject 标记用于提供依赖的方法
     * <p>
     * 构造器注入的局限：如果有多个构造器，我们只能标注其中一个，无法标注多个
     * <p>
     * 标注在public方法上，Dagger2会在构造器执行之后立即调用这个方法，可以提供this对象
     */
    @Inject
    public RxFlux() {
        mActivityCounter = 0;
        mActivityStack = new Stack<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        mActivityCounter++;
        mActivityStack.add(activity);
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getLifecycle()
                    .addObserver(new RxActivityLifecycleObserver(activity));
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(this, true);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentPreAttached(fm, f, context);
    }

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentAttached(fm, f, context);
        f.getLifecycle().addObserver(new RxFragmentLifecycleObserver(f));
    }

    @Override
    public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentPreCreated(fm, f, savedInstanceState);
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
    }


    /**
     * RxFluxView能响应时，注册订阅
     *
     * @param activity
     */
    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof RxSubscriberView) {
            if (mRxDispatcher.isSubscribe(this)) {
                return;
            }
            Log.w("RxFlux", "10.1-subscribe RxFluxActivity : " + activity.getClass().getSimpleName());
            mRxDispatcher.subscribeRxView((RxSubscriberView) activity);
        }
    }

    /**
     * RxFluxView能响应时，注册订阅
     *
     * @param fm
     * @param f
     */
    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        if (f instanceof RxSubscriberView) {
            if (mRxDispatcher.isSubscribe(this)) {
                return;
            }
            Log.w("RxFlux", "11.1-subscribe RxFluxFragment : " + f.getClass().getSimpleName());
            mRxDispatcher.subscribeRxView((RxSubscriberView) f);
        }
    }


    /**
     * RxFluxView不能响应时，取消订阅
     *
     * @param activity
     */
    @Override
    public void onActivityPaused(Activity activity) {
        if (activity instanceof RxSubscriberView) {
            mRxDispatcher.unsubscribeRxView((RxSubscriberView) activity);
            Log.w("RxFlux", "12.1-unsubscribe RxFluxActivity : " + activity.getClass().getSimpleName());
        }
    }

    /**
     * RxFluxView不能响应时，取消订阅
     *
     * @param fm
     * @param f
     */
    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
        if (f instanceof RxSubscriberView) {
            mRxDispatcher.unsubscribeRxView((RxSubscriberView) f);
            Log.w("RxFlux", "13.1-unsubscribe RxFluxFragment : " + f.getClass().getSimpleName());
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityCounter--;
        mActivityStack.remove(activity);
        if (mActivityCounter == 0 || mActivityStack.size() == 0) {
            shutdown();
        }
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDetached(fm, f);
    }


    public void finishAllActivity() {
        while (!mActivityStack.empty()) {
            mActivityStack.pop().finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        finishActivity(getActivity(cls));
    }

    private void shutdown() {
        mRxActionManager.clear();
        mRxDispatcher.unsubscribeAll();
    }

    private void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    private Activity getActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }
}
