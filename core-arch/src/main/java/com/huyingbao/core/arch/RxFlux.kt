package com.huyingbao.core.arch

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver
import com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver
import com.huyingbao.core.arch.view.RxSubscriberView

import java.util.Stack

import javax.inject.Inject
import javax.inject.Singleton

/**
 * 自动跟踪Activity/Fragment的生命周期，管理Activity/Fragment订阅，必须在Application创建的时候调用该类的实例方法, 并仅调用一次。
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class RxFlux
/**
 * Inject 标记用于提供依赖的方法
 *
 *
 * 构造器注入的局限：如果有多个构造器，我们只能标注其中一个，无法标注多个
 *
 *
 * 标注在public方法上，Dagger2会在构造器执行之后立即调用这个方法，可以提供this对象
 */
@Inject
constructor() : FragmentManager.FragmentLifecycleCallbacks(), Application.ActivityLifecycleCallbacks {
    /**
     * Inject 用来标记需要注入的依赖
     * 被标注的属性不能使用private修饰，否则无法注入
     *
     * @param rxBus
     */
    @Inject
    internal var mRxDispatcher: RxDispatcher? = null
    @Inject
    internal var mRxActionManager: RxActionManager? = null

    private var mActivityCounter: Int = 0
    private val mActivityStack: Stack<Activity>

    init {
        mActivityCounter = 0
        mActivityStack = Stack()
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle) {
        mActivityCounter++
        mActivityStack.add(activity)
        if (activity is FragmentActivity) {
            activity.lifecycle
                    .addObserver(RxActivityLifecycleObserver(activity))
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(this, true)
        }
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        f.lifecycle.addObserver(RxFragmentLifecycleObserver(f))
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
    }


    /**
     * [RxSubscriberView]注册订阅
     */
    override fun onActivityResumed(activity: Activity) {
        if (activity is RxSubscriberView) {
            if (mRxDispatcher!!.isSubscribe(this)) {
                return
            }
            Log.i(TAG, "Subscribe RxFluxActivity : " + activity.javaClass.getSimpleName())
            mRxDispatcher!!.subscribeRxView(activity as RxSubscriberView)
        }
    }

    /**
     * [RxSubscriberView]注册订阅
     */
    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        if (f is RxSubscriberView) {
            if (mRxDispatcher!!.isSubscribe(this)) {
                return
            }
            Log.i(TAG, "Subscribe RxFluxFragment : " + f.javaClass.getSimpleName())
            mRxDispatcher!!.subscribeRxView(f as RxSubscriberView)
        }
    }


    /**
     * [RxSubscriberView]取消订阅
     */
    override fun onActivityPaused(activity: Activity) {
        if (activity is RxSubscriberView) {
            Log.i(TAG, "Unsubscribe RxFluxActivity : " + activity.javaClass.getSimpleName())
            mRxDispatcher!!.unsubscribeRxView(activity as RxSubscriberView)
        }
    }

    /**
     * [RxSubscriberView]取消订阅
     */
    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        if (f is RxSubscriberView) {
            Log.i(TAG, "Unsubscribe RxFluxFragment : " + f.javaClass.getSimpleName())
            mRxDispatcher!!.unsubscribeRxView(f as RxSubscriberView)
        }
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        super.onFragmentSaveInstanceState(fm, f, outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
        mActivityCounter--
        mActivityStack.remove(activity)
        if (mActivityCounter == 0 || mActivityStack.size == 0) {
            shutdown()
        }
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
    }


    fun finishAllActivity() {
        while (!mActivityStack.empty()) {
            mActivityStack.pop().finish()
        }
    }

    fun finishActivity(cls: Class<*>) {
        finishActivity(getActivity(cls))
    }

    private fun shutdown() {
        mRxActionManager!!.clear()
        mRxDispatcher!!.unsubscribeAll()
    }

    private fun finishActivity(activity: Activity?) {
        if (activity != null) {
            mActivityStack.remove(activity)
            activity.finish()
        }
    }

    private fun getActivity(cls: Class<*>): Activity? {
        for (activity in mActivityStack) {
            if (activity.javaClass == cls) {
                return activity
            }
        }
        return null
    }

    companion object {
        val TAG = "RxFlux"
    }
}
