package com.huyingbao.core.common.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.view.RxFluxView
import com.huyingbao.core.common.R
import com.huyingbao.core.common.model.CommonHttpException
import com.huyingbao.core.common.util.ActivityUtils
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.reflect.ParameterizedType
import javax.inject.Inject


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2019/1/1.
 */
abstract class CommonRxActivity<T : RxActivityStore> : AppCompatActivity(), CommonView, RxFluxView<T>, HasSupportFragmentInjector {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var mChildFragmentInjector: DispatchingAndroidInjector<Fragment>

    private var mStore: T? = null

    override val rxStore: T?
        get() {
            if (mStore == null) {
                val tClass = (javaClass.getGenericSuperclass() as ParameterizedType).actualTypeArguments[0] as Class<T>
                mStore = ViewModelProviders.of(this, mViewModelFactory).get(tClass)
            }
            return mStore
        }

    override val layoutId: Int
        get() = R.layout.common_activity_fragment_base

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return mChildFragmentInjector
    }

    /**
     * 提供activity需要显示的fragment
     *
     * @return
     */
    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        Log.v("RxFlux", "1.1-onCreateActivity")
        setContentView(layoutId)
        afterCreate(savedInstanceState)
        ButterKnife.bind(this)
        //添加显示的Fragment
        val fragmentManager = supportFragmentManager
        //从fragment队列中获取资源ID标识的fragment
        val fragment = fragmentManager.findFragmentById(R.id.fl_content)
        if (fragment != null) return
        fragmentManager.beginTransaction().add(R.id.fl_content, createFragment()).commit()
    }

    /**
     * 接收RxChange，粘性
     */
    @CallSuper//强制子类复写该方法时调用父方法
    @Subscribe(sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxChange)
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由activity直接处理
     */
    @Subscribe(sticky = true)
    override fun onRxError(error: RxError) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(error)
        val throwable = error.throwable
        // 自定义异常
        if (throwable is CommonHttpException) {
            val message = throwable.message()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home// 点击返回图标事件
            -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    return true
                }
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，隐藏旧的Fragment，添加新的Fragment
     *
     * @param fragment
     */
    protected fun addFragmentHideExisting(fragment: Fragment) {
        ActivityUtils.setFragment(this, R.id.fl_content, fragment, true)
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，用新的Fragment替换旧的Fragment
     *
     * @param fragment
     */
    protected fun addFragmentReplaceExisting(fragment: Fragment) {
        ActivityUtils.setFragment(this, R.id.fl_content, fragment, false)
    }

    companion object {
        init {//Vector使用
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
