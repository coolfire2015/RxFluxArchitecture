package com.huyingbao.core.arch

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver
import com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver
import com.huyingbao.core.arch.view.RxFluxView

import java.util.Stack

import javax.inject.Inject
import javax.inject.Singleton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * 主类
 * 必须在application创建的时候调用该类的实例方法, 并仅调用一次.
 * 这个类会自动跟踪应用程序的生命周期,
 * 并且注销每个activity剩余的订阅subscriptions
 *
 *
 * 抽象的类并不能实例化
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
    lateinit var mRxDispatcher: RxDispatcher
    @Inject
    lateinit var mRxActionManager: RxActionManager

    private var mActivityCounter: Int = 0
    private val mActivityStack: Stack<Activity>

    init {
        mActivityCounter = 0
        mActivityStack = Stack()
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle) {
        Log.v("RxFlux", "1-onActivityCreated")
        mActivityCounter++
        mActivityStack.add(activity)
        if (activity is FragmentActivity) {
            activity.lifecycle
                    .addObserver(RxActivityLifecycleObserver(activity))
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(this, true)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        Log.v("RxFlux", "2-onActivityStarted")
        if (activity is RxFluxView<*>) {
            if (mRxDispatcher!!.isSubscribe(this)) return
            Log.v("RxFlux", "2.1-onActivityRegistered")
            mRxDispatcher!!.subscribeRxView<RxFluxView>(activity as RxFluxView<*>)
        }
    }

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        Log.v("RxFlux", "3-onFragmentPreAttached")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        Log.v("RxFlux", "4-onFragmentAttached")
        f.lifecycle.addObserver(RxFragmentLifecycleObserver(f))
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
        Log.v("RxFlux", "5-onFragmentPreCreated")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        Log.v("RxFlux", "6-onFragmentCreated")
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        Log.v("RxFlux", "7-onFragmentViewCreated")
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
        Log.v("RxFlux", "8-onFragmentActivityCreated")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        Log.v("RxFlux", "9-onFragmentStarted")
        if (f is RxFluxView<*>) {
            if (mRxDispatcher!!.isSubscribe(this)) return
            Log.v("RxFlux", "9.1-onFragmentRegistered")
            mRxDispatcher!!.subscribeRxView<RxFluxView>(f as RxFluxView<*>)
        }
    }


    override fun onActivityResumed(activity: Activity) {
        Log.v("RxFlux", "10-onActivityResumed")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        Log.v("RxFlux", "11-onFragmentResumed")
    }


    override fun onActivityPaused(activity: Activity) {
        Log.v("RxFlux", "12-onActivityPaused")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        Log.v("RxFlux", "13-onFragmentPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.v("RxFlux", "14-onActivityStopped")
        if (activity is RxFluxView<*>)
            mRxDispatcher!!.unsubscribeRxView<RxFluxView>(activity as RxFluxView<*>)
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        Log.v("RxFlux", "15-onFragmentStopped")
        if (f is RxFluxView<*>)
            mRxDispatcher!!.unsubscribeRxView<RxFluxView>(f as RxFluxView<*>)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        Log.v("RxFlux", "16-onActivitySaveInstanceState")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        Log.v("RxFlux", "17-onFragmentSaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.v("RxFlux", "18-onActivityDestroyed")
        mActivityCounter--
        mActivityStack.remove(activity)
        if (mActivityCounter == 0 || mActivityStack.size == 0)
            shutdown()
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        Log.v("RxFlux", "19-onFragmentViewDestroyed")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Log.v("RxFlux", "20-onFragmentDestroyed")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        Log.v("RxFlux", "21-onFragmentDetached")
    }


    fun finishAllActivity() {
        while (!mActivityStack.empty())
            mActivityStack.pop().finish()
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
        for (activity in mActivityStack)
            if (activity.javaClass == cls)
                return activity
        return null
    }
}
