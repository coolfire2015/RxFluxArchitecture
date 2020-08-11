package com.huyingbao.module.common.app

import androidx.appcompat.app.AppCompatActivity
import com.huyingbao.core.annotations.AppObserver
import com.huyingbao.core.arch.FluxLifecycleCallback
import com.huyingbao.core.arch.model.Error
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.core.arch.store.AppStore
import com.huyingbao.core.base.flux.activity.BaseFluxActivity
import com.huyingbao.module.common.dialog.CommonLoadingDialog
import com.huyingbao.module.common.dialog.CommonLoadingDialogClickListener
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局Store，可以接受全局的[Error]、[Loading]。
 *
 * 通过[FluxLifecycleCallback]持有的Activity栈，来进行对应的操作。
 *
 * Created by liujunfeng on 2019/8/1.
 */
@Singleton
@AppObserver
class CommonAppStore @Inject constructor(
        private val fluxLifecycleCallback: FluxLifecycleCallback
) : AppStore() {
    /**
     * 接收[Error]，粘性
     */
    @Subscribe(sticky = true)
    fun onError(error: Error) {
        val activity = fluxLifecycleCallback.activityStack.peek() ?: return
        when (val throwable = error.throwable) {
            is HttpException -> activity.toast("${throwable.code()}:${throwable.message()}")
            is SocketException -> activity.toast("网络异常!")
            is UnknownHostException -> activity.toast("网络异常!")
            is SocketTimeoutException -> activity.toast("连接超时!")
            else -> activity.toast(throwable.toString())
        }
    }

    /**
     * 显示或隐藏进度对话框，接收[Loading]，粘性
     */
    @Subscribe(sticky = true)
    fun onLoading(loading: Loading) {
        val activity = fluxLifecycleCallback.activityStack.peek() ?: return
        if (activity is AppCompatActivity) {
            val fragmentByTag = activity.supportFragmentManager.findFragmentByTag(loading.tag)
            if (fragmentByTag == null && loading.isLoading) {
                //显示进度框
                val commonLoadingDialog = CommonLoadingDialog.newInstance()
                commonLoadingDialog.clickListener = object : CommonLoadingDialogClickListener {
                    override fun onCancel() {
                        if (activity is BaseFluxActivity<*>) {
                            activity.baseActionCreator.removeAction(tag = loading.tag)
                            activity.toast("取消操作${loading.tag}")
                        }
                    }
                }
                commonLoadingDialog.show(activity.supportFragmentManager, loading.tag)
                return
            }
            if (fragmentByTag is CommonLoadingDialog && !loading.isLoading) {
                //隐藏进度框
                fragmentByTag.dismiss()
            }
        }
    }
}