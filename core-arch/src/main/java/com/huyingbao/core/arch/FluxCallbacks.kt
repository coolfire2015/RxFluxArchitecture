package com.huyingbao.core.arch

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.huyingbao.core.arch.action.FluxActionManager
import com.huyingbao.core.arch.dispatcher.FluxDispatcher
import com.huyingbao.core.arch.dispatcher.FluxSubscriber
import com.huyingbao.core.arch.view.FluxView
import java.util.*
import javax.inject.Inject
import androidx.lifecycle.DefaultLifecycleObserver as LifecycleDefaultLifecycleObserver

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
object FluxCallbacks : FragmentManager.FragmentLifecycleCallbacks(), Application.ActivityLifecycleCallbacks {
    const val TAG = "Flux"

    /**
     * 当前维护的Activity个数
     */
    private var activityCounter: Int = 0

    /**
     * 维护的应用内Activity栈
     */
    val activityStack: Stack<Activity> = Stack()

    /**
     * 注册[Activity]持有的[com.huyingbao.core.arch.store.FluxStore]
     */
    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        activityCounter++
        activityStack.push(activity)
        if (activity !is FragmentActivity) return
        // 注册 fragment 生命周期回调
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)
        // 注册持有的 FluxStore
        subscribeStore(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    /**
     * 注册[Fragment]持有的[com.huyingbao.core.arch.store.FluxStore]
     */
    override fun onFragmentAttached(fragmentManager: FragmentManager, fragment: Fragment, context: Context) {
        super.onFragmentAttached(fragmentManager, fragment, context)
        // 注册持有的 FluxStore
        subscribeStore(fragment)
    }

    /**
     * 注册[Activity]订阅
     */
    override fun onActivityResumed(activity: Activity) {
        if (activity is FluxSubscriber) {
            if (FluxDispatcher.isSubscribe(activity)) {
                return
            }
            Log.i(TAG, "Subscribe FluxActivity : " + activity.javaClass.simpleName)
            FluxDispatcher.subscribe(activity)
        }
    }

    /**
     * 注册[Fragment]订阅
     */
    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentResumed(fragmentManager, fragment)
        if (fragment is FluxSubscriber) {
            if (FluxDispatcher.isSubscribe(fragment)) {
                return
            }
            Log.i(TAG, "Subscribe FluxFragment : " + fragment.javaClass.simpleName)
            FluxDispatcher.subscribe(fragment)
        }
    }

    /**
     * 取消[Activity]订阅
     */
    override fun onActivityPaused(activity: Activity) {
        if (activity is FluxSubscriber) {
            Log.i(TAG, "Unsubscribe FluxActivity : " + activity.javaClass.simpleName)
            FluxDispatcher.unsubscribe(activity)
        }
    }

    /**
     * 取消[Fragment]订阅
     */
    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentPaused(fragmentManager, fragment)
        if (fragment is FluxSubscriber) {
            Log.i(TAG, "Unsubscribe FluxFragment : " + fragment.javaClass.simpleName)
            FluxDispatcher.unsubscribe(fragment)
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
        FluxActionManager.clear()
        FluxDispatcher.unsubscribeAll()
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

    /**
     * 注册持有的[com.huyingbao.core.arch.store.FluxStore]
     */
    private fun subscribeStore(lifecycleOwner: LifecycleOwner) {
        if (lifecycleOwner !is FluxView) return
        // 添加生命周期监听器
        lifecycleOwner.lifecycle.addObserver(object : LifecycleDefaultLifecycleObserver {
            /**
             * 在onCreate(Bundle)完成依赖注入之后调用
             */
            override fun onCreate(owner: LifecycleOwner) {
                val store = (owner as FluxView).store
                if (FluxDispatcher.isSubscribe(store)) return
                Log.i(TAG, "Subscribe Store : " + javaClass.simpleName)
                FluxDispatcher.subscribe(store)
            }
        })

    }
}

