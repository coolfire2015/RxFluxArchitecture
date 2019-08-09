package com.huyingbao.core.arch


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import dagger.android.DaggerApplication
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject

/**
 * Application实现Dagger.Android依赖注入。
 *
 * 通过反射获取RxAppLifecycleOwner，并实现生命周期状态分发到[RxAppLifecycle]。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxApp : DaggerApplication() {
    @Inject
    lateinit var rxFlux: RxFlux

    private var lifecycleOwner: LifecycleOwner? = null

    private val annotationGeneratedRxAppLifecycleOwner: LifecycleOwner?
        get() {
            var result: LifecycleOwner? = null
            try {
                val clazz = Class.forName("com.huyingbao.core.arch.RxAppLifecycleOwner") as Class<LifecycleOwner>
                result = clazz.getConstructor(Application::class.java).newInstance(this)
            } catch (e: ClassNotFoundException) {
                if (Log.isLoggable(RxFlux.TAG, Log.WARN)) {
                    Log.w(RxFlux.TAG, "Failed to find RxAppLifecycleOwner. You should include an"
                            + " annotationProcessor compile dependency on com.github.coolfire2015.RxFluxArchitecture:core-arch-processor"
                            + " in your application and a @RxAppObserver annotated RxAppLifecycle subclass"
                            + " and a @RxAppOwner annotated RxApp implementation")
                }
            } catch (e: InstantiationException) {
                throwIncorrectRxAppLifecycle(e)
            } catch (e: IllegalAccessException) {
                throwIncorrectRxAppLifecycle(e)
            } catch (e: NoSuchMethodException) {
                throwIncorrectRxAppLifecycle(e)
            } catch (e: InvocationTargetException) {
                throwIncorrectRxAppLifecycle(e)
            }
            return result
        }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        lifecycleOwner = annotationGeneratedRxAppLifecycleOwner
    }

    override fun onCreate() {
        super.onCreate()
        //application创建的时候调用该方法，使RxFlux可以接受Activity生命周期回调
        registerActivityLifecycleCallbacks(rxFlux)
        (lifecycleOwner?.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        (lifecycleOwner?.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onTerminate() {
        super.onTerminate()
        (lifecycleOwner?.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    private fun throwIncorrectRxAppLifecycle(e: Exception) {
        throw IllegalStateException("RxAppLifecycleOwner is implemented incorrectly."
                + " If you've manually implemented this class, remove your implementation. The Annotation"
                + " processor will generate a correct implementation.", e)
    }
}