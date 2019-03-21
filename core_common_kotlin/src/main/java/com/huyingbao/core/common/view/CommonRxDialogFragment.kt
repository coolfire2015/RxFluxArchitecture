package com.huyingbao.core.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.Unbinder
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.view.RxFluxView
import dagger.android.support.AndroidSupportInjection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
abstract class CommonRxDialogFragment<T : ViewModel> : AppCompatDialogFragment(), CommonView, RxFluxView<T> {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mStore: T? = null
    private var mUnbinder: Unbinder? = null

    override val rxStore: T?
        get() {
            if (mStore == null) {
                val tClass = (javaClass.getGenericSuperclass() as ParameterizedType).actualTypeArguments[0] as Class<T>
                mStore = ViewModelProviders.of(activity!!, mViewModelFactory).get(tClass)
            }
            return mStore
        }

    override fun onAttach(context: Context) {
        //依赖注入
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //设置布局
        val rootView = LayoutInflater.from(activity).inflate(layoutId, null)
        //ButterKnife绑定
        mUnbinder = ButterKnife.bind(this, rootView)
        //创建AlertDialog
        val dialog = AppCompatDialog(context, theme)
        dialog.setContentView(rootView)
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // view创建之后的操作
        afterCreate(savedInstanceState)
    }

    /**
     * 接收RxChange，粘性
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxChange)
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由fragment直接处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    override fun onRxError(error: RxError) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnbinder!!.unbind()
    }
}
