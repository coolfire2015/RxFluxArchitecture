package com.huyingbao.core.arch

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver
import com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver
import com.huyingbao.core.arch.view.RxSubscriberView
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 自动跟踪Activity/Fragment的生命周期，管理Activity/Fragment订阅。
 *
 * 必须在Application创建的时候调用该类的实例方法, 并仅调用一次。
 *
 * [Inject]标记用于提供依赖的方法
 *
 * 构造器注入的局限：如果有多个构造器，我们只能标注其中一个，无法标注多个
 *
 * 标注在public方法上，Dagger2会在构造器执行之后立即调用这个方法，可以提供this对象
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class RxFlux @Inject constructor() :
        FragmentManager.FragmentLifecycleCallbacks(),
        Application.ActivityLifecycleCallbacks {
    /**
     * [Inject] 用来标记需要注入的依赖, 被标注的属性不能使用private修饰，否则无法注入
     */
    @Inject
    lateinit var rxDispatcher: RxDispatcher
    @Inject
    lateinit var rxActionManager: RxActionManager

    private var activityCounter: Int = 0
    private val activityStack: Stack<Activity> = Stack()

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        activityCounter++
        activityStack.add(activity)
        if (activity is FragmentActivity) {
            activity.lifecycle.addObserver(RxActivityLifecycleObserver(activity))
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)
        }
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        f.lifecycle.addObserver(RxFragmentLifecycleObserver(f))
    }

    /**
     * [RxSubscriberView]注册订阅
     */
    override fun onActivityResumed(activity: Activity?) {
        if (activity is RxSubscriberView) {
            if (rxDispatcher.isSubscribe(this)) {
                return
            }
            Log.i(TAG, "Subscribe RxFluxActivity : " + activity.javaClass.simpleName)
            rxDispatcher.subscribeRxView(activity as RxSubscriberView)
        }
    }

    /**
     * [RxSubscriberView]注册订阅
     */
    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        if (f is RxSubscriberView) {
            if (rxDispatcher.isSubscribe(this)) {
                return
            }
            Log.i(TAG, "Subscribe RxFluxFragment : " + f.javaClass.simpleName)
            rxDispatcher.subscribeRxView(f as RxSubscriberView)
        }
    }

    /**
     * [RxSubscriberView]取消订阅
     */
    override fun onActivityPaused(activity: Activity?) {
        if (activity is RxSubscriberView) {
            Log.i(TAG, "Unsubscribe RxFluxActivity : " + activity.javaClass.simpleName)
            rxDispatcher.unsubscribeRxView(activity as RxSubscriberView)
        }
    }

    /**
     * [RxSubscriberView]取消订阅
     */
    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        if (f is RxSubscriberView) {
            Log.i(TAG, "Unsubscribe RxFluxFragment : " + f.javaClass.simpleName)
            rxDispatcher.unsubscribeRxView(f as RxSubscriberView)
        }
    }

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {}

    override fun onActivityDestroyed(activity: Activity?) {
        activityCounter--
        activityStack.remove(activity)
        if (activityCounter == 0 || activityStack.size == 0) {
            shutdown()
        }
    }

    fun finishAllActivity() {
        while (!activityStack.empty()) {
            activityStack.pop().finish()
        }
    }

    fun finishActivity(cls: Class<*>) {
        finishActivity(getActivity(cls))
    }

    private fun shutdown() {
        rxActionManager.clear()
        rxDispatcher.unsubscribeAll()
    }

    private fun finishActivity(activity: Activity?) {
        if (activity != null) {
            activityStack.remove(activity)
            activity.finish()
        }
    }

    private fun getActivity(cls: Class<*>): Activity? {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                return activity
            }
        }
        return null
    }

    companion object {
        const val TAG = "RxFlux"
    }
}
