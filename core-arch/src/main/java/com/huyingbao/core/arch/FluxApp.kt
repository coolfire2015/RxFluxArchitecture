package com.huyingbao.core.arch


import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import java.lang.reflect.InvocationTargetException

/**
 * 一个Project中只能有一个该类的子类。
 *
 * 如子模块中需要观察主模块的生命周期，需要使用[com.huyingbao.core.annotations.AppOwner]注解标注Project中唯一子类实现，
 *
 * 并在子模块中创建[com.huyingbao.core.annotations.AppObserver]注解标注的Application生命周期观察者[androidx.lifecycle.LifecycleObserver]子类实现。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class FluxApp : Application() {
    /**
     * 通过反射获取壳module中编译自动生成的[LifecycleOwner]子类[com.huyingbao.core.arch.FinalAppLifecycleOwner]
     */
    private val lifecycleOwner: LifecycleOwner? by lazy {
        var result: LifecycleOwner? = null
        try {
            val clazz =
                Class.forName("com.huyingbao.core.arch.FinalAppLifecycleOwner") as Class<LifecycleOwner>
            result = clazz.getConstructor(Application::class.java).newInstance(this)
        } catch (e: ClassNotFoundException) {
            if (Log.isLoggable(FluxCallbacks.TAG, Log.WARN)) {
                Log.w(
                    FluxCallbacks.TAG, "Failed to find AppLifecycleOwner. You should include an"
                            + " annotationProcessor compile dependency on com.github.coolfire2015.RxFluxArchitecture:core-arch-processor"
                            + " in your application and a @FluxAppObserver annotated FluxAppLifecycle subclass"
                            + " and a @FluxAppOwner annotated FluxApp implementation"
                )
            }
        } catch (e: InstantiationException) {
            throwIncorrectFluxAppLifecycle(e)
        } catch (e: IllegalAccessException) {
            throwIncorrectFluxAppLifecycle(e)
        } catch (e: NoSuchMethodException) {
            throwIncorrectFluxAppLifecycle(e)
        } catch (e: InvocationTargetException) {
            throwIncorrectFluxAppLifecycle(e)
        }
        result
    }

    companion object {
        var application: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        //静态内部类持有当前Application
        application = this
        //application创建的时候调用该方法，使FluxLifecycleCallback可以接受Activity生命周期回调
        registerActivityLifecycleCallbacks(FluxCallbacks)
        //分发ON_CREATE状态
        lifecycleOwner?.let {
            (it.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        //分发ON_STOP状态
        lifecycleOwner?.let {
            (it.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        //分发ON_DESTROY状态
        lifecycleOwner?.let {
            (it.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
    }

    private fun throwIncorrectFluxAppLifecycle(e: Exception) {
        throw IllegalStateException(
            "AppLifecycleOwner is implemented incorrectly."
                    + " If you've manually implemented this class, remove your implementation. The Annotation"
                    + " processor will generate a correct implementation.", e
        )
    }
}
