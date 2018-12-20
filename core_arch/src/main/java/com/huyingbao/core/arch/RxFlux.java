package com.huyingbao.core.arch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver;
import com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver;
import com.huyingbao.core.arch.view.RxFluxView;

import java.util.Stack;

import javax.inject.Inject;
import javax.inject.Singleton;

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
        mActivityCounter++;
        mActivityStack.add(activity);
        if (activity instanceof FragmentActivity)
            ((FragmentActivity) activity).getLifecycle()
                    .addObserver(new RxActivityLifecycleObserver(activity));
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof RxFluxView)
            mRxDispatcher.subscribeRxView((RxFluxView) activity);
        if (activity instanceof FragmentActivity)
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(this, true);
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof RxFluxView)
            mRxDispatcher.unsubscribeRxView((RxFluxView) activity);
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
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
        f.getLifecycle().addObserver(new RxFragmentLifecycleObserver(f));
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        if (f instanceof RxFluxView)
            mRxDispatcher.subscribeRxView((RxFluxView) f);
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        if (f instanceof RxFluxView)
            mRxDispatcher.unsubscribeRxView((RxFluxView) f);
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
