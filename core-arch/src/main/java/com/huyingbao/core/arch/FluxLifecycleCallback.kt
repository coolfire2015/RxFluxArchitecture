package com.huyingbao.core.arch

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.lifecycle.ActivityLifecycleObserver
import com.huyingbao.core.arch.lifecycle.FragmentLifecycleObserver
import com.huyingbao.core.arch.view.SubscriberView
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
class FluxLifecycleCallback @Inject constructor() : FragmentManager.FragmentLifecycleCallbacks(), Application.ActivityLifecycleCallbacks {
    /**
     * [Inject] 用来标记需要注入的依赖, 被标注的属性不能使用private修饰，否则无法注入
     */
    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var mActionManager: ActionManager

    /**
     * 当前维护的Activity个数
     */
    private var activityCounter: Int = 0

    /**
     * 维护的应用内Activity栈
     */
    val activityStack: Stack<Activity> = Stack()

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        activityCounter++
        activityStack.push(activity)
        if (activity is FragmentActivity) {
            activity.lifecycle.addObserver(ActivityLifecycleObserver(activity))
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onFragmentAttached(fragmentManager: FragmentManager, fragment: Fragment, context: Context) {
        super.onFragmentAttached(fragmentManager, fragment, context)
        fragment.lifecycle.addObserver(FragmentLifecycleObserver(fragment))
    }

    /**
     * [SubscriberView]注册订阅
     */
    override fun onActivityResumed(activity: Activity) {
        if (activity is SubscriberView) {
            if (dispatcher.isSubscribe(activity)) {
                return
            }
            Log.i(TAG, "Subscribe FluxActivity : " + activity.javaClass.simpleName)
            dispatcher.subscriberView(activity as SubscriberView)
        }
    }

    /**
     * [SubscriberView]注册订阅
     */
    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentResumed(fragmentManager, fragment)
        if (fragment is SubscriberView) {
            if (dispatcher.isSubscribe(fragment)) {
                return
            }
            Log.i(TAG, "Subscribe FluxFragment : " + fragment.javaClass.simpleName)
            dispatcher.subscriberView(fragment as SubscriberView)
        }
    }

    /**
     * [SubscriberView]取消订阅
     */
    override fun onActivityPaused(activity: Activity) {
        if (activity is SubscriberView) {
            Log.i(TAG, "Unsubscribe FluxActivity : " + activity.javaClass.simpleName)
            dispatcher.unsubscriberView(activity as SubscriberView)
        }
    }

    /**
     * [SubscriberView]取消订阅
     */
    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentPaused(fragmentManager, fragment)
        if (fragment is SubscriberView) {
            Log.i(TAG, "Unsubscribe FluxFragment : " + fragment.javaClass.simpleName)
            dispatcher.unsubscriberView(fragment as SubscriberView)
        }
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        activityCounter--
        activityStack.remove(activity)
        if (activityCounter == 0 || activityStack.size == 0) {
            shutdown()
        }
    }

    /**
     * 结束所有的 Activity
     */
    fun finishAllActivity() {
        while (!activityStack.empty()) {
            activityStack.pop().finish()
        }
    }

    /**
     * 结束指定的 Activity
     */
    fun finishActivity(cls: Class<*>) {
        finishActivity(getActivity(cls))
    }

    private fun shutdown() {
        mActionManager.clear()
        dispatcher.unsubscribeAll()
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
        const val TAG = "Flux"
    }
}
