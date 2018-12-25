package com.huyingbao.core.arch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver;
import com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver;
import com.huyingbao.core.arch.view.RxFluxView;

import java.util.Stack;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * 主类
 * 必须在application创建的时候调用该类的实例方法, 并仅调用一次.
 * 这个类会自动跟踪应用程序的生命周期,
 * 并且注销每个activity剩余的订阅subscriptions
 * <p>
 * 抽象的类并不能实例化
 * Created by liujunfeng on 2017/12/7.
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
        Log.e("RxFlux", "1-onActivityCreated");
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
        Log.e("RxFlux", "2-onActivityStarted");
        if (activity instanceof RxFluxView) {
            if (mRxDispatcher.isSubscribe(this)) return;
            Log.e("RxFlux", "2.1-onActivityRegistered");
            mRxDispatcher.subscribeRxView((RxFluxView) activity);
        }
    }

    @Override
    public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentPreAttached(fm, f, context);
        Log.e("RxFlux", "3-onFragmentPreAttached");
    }

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentAttached(fm, f, context);
        Log.e("RxFlux", "4-onFragmentAttached");
        f.getLifecycle().addObserver(new RxFragmentLifecycleObserver(f));
    }

    @Override
    public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentPreCreated(fm, f, savedInstanceState);
        Log.e("RxFlux", "5-onFragmentPreCreated");
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        Log.e("RxFlux", "6-onFragmentCreated");
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        Log.e("RxFlux", "7-onFragmentViewCreated");
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        Log.e("RxFlux", "8-onFragmentActivityCreated");
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        Log.e("RxFlux", "9-onFragmentStarted");
        if (f instanceof RxFluxView) {
            if (mRxDispatcher.isSubscribe(this)) return;
            Log.e("RxFlux", "9.1-onFragmentRegistered");
            mRxDispatcher.subscribeRxView((RxFluxView) f);
        }
    }


    @Override
    public void onActivityResumed(Activity activity) {
        Log.e("RxFlux", "10-onActivityResumed");
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        Log.e("RxFlux", "11-onFragmentResumed");
    }


    @Override
    public void onActivityPaused(Activity activity) {
        Log.e("RxFlux", "12-onActivityPaused");
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
        Log.e("RxFlux", "13-onFragmentPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("RxFlux", "14-onActivityStopped");
        if (activity instanceof RxFluxView)
            mRxDispatcher.unsubscribeRxView((RxFluxView) activity);
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        Log.e("RxFlux", "15-onFragmentStopped");
        if (f instanceof RxFluxView)
            mRxDispatcher.unsubscribeRxView((RxFluxView) f);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.e("RxFlux", "16-onActivitySaveInstanceState");
    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
        Log.e("RxFlux", "17-onFragmentSaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("RxFlux", "18-onActivityDestroyed");
        mActivityCounter--;
        mActivityStack.remove(activity);
        if (mActivityCounter == 0 || mActivityStack.size() == 0)
            shutdown();
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        Log.e("RxFlux", "19-onFragmentViewDestroyed");
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
        Log.e("RxFlux", "20-onFragmentDestroyed");
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDetached(fm, f);
        Log.e("RxFlux", "21-onFragmentDetached");
    }


    public void finishAllActivity() {
        while (!mActivityStack.empty())
            mActivityStack.pop().finish();
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
        for (Activity activity : mActivityStack)
            if (activity.getClass().equals(cls))
                return activity;
        return null;
    }
}
